/*
 * This file is part of SpoutcraftAPI (http://wiki.getspout.org/).
 * 
 * SpoutcraftAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutcraftAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spoutcraft.spoutcraftapi.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.lwjgl.opengl.GL11;
import org.spoutcraft.spoutcraftapi.Spoutcraft;
import org.spoutcraft.spoutcraftapi.UnsafeClass;
import org.spoutcraft.spoutcraftapi.addon.Addon;

@UnsafeClass
public abstract class GenericScreen extends GenericWidget implements Screen {
	protected HashMap<Widget, Addon> widgets = new HashMap<Widget, Addon>();
	protected int playerId;
	protected boolean bgvis;
	protected int mouseX = -1, mouseY = -1;

	public GenericScreen() {
		screenWidth = Spoutcraft.getClient().getRenderDelegate().getScreenWidth();
		screenHeight = Spoutcraft.getClient().getRenderDelegate().getScreenHeight();
	}

	public GenericScreen(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public int getVersion() {
		return super.getVersion() + 0;
	}

	public Widget[] getAttachedWidgets() {
		return getAttachedWidgets(false);
	} 
	
	public Widget[] getAttachedWidgets(boolean recursive) {
		Widget[] list = new Widget[widgets.size()];
		Set<Widget> allwidgets = new HashSet<Widget>();
		allwidgets.addAll(widgets.keySet());
		if(recursive) {
			for(Widget w:widgets.keySet()) {
				if(w instanceof Screen) {
					allwidgets.addAll(((Screen)w).getAttachedWidgetsAsSet(true));
				}
			}
		}
		list = allwidgets.toArray(list);
		return list;
	}

	@Deprecated
	public Screen attachWidget(Widget widget) {
		return attachWidget(null, widget);
	}
	
	public Screen attachWidget(Addon addon, Widget widget) {
		widgets.put(widget, addon);
		//widget.setAddon(addon);
		widget.setScreen(this);
		return this;
	}

	public Screen removeWidget(Widget widget) {
		widgets.remove(widget);
		widget.setScreen(null);
		return this;
	}
	
	public Screen removeWidgets(Addon addon) {
		for (Widget i : getAttachedWidgets()) {
			if (widgets.get(i) != null && widgets.get(i).equals(addon)) {
				removeWidget(i);
			}
		}
		return this;
	}
	
	public boolean containsWidget(Widget widget) {
		return containsWidget(widget.getId());
	}
	
	public boolean containsWidget(UUID id) {
		return getWidget(id) != null;
	}
	
	public Widget getWidget(UUID id) {
		for (Widget w : widgets.keySet()) {
			if (w.getId().equals(id)) {
				return w;
			}
		}
		return null;
	}
	
	public boolean updateWidget(Widget widget) {
		if (widgets.containsKey(widget)) {
			Addon addon = widgets.get(widget);
			widgets.remove(widget);
			widgets.put(widget, addon);
			widget.setScreen(this);
			return true;
		}
		return false;
	}

	@Override
	public void onTick() {
		for (Widget widget : widgets.keySet()) {
			widget.onAnimate();
			try {
				widget.onTick();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		screenWidth = Spoutcraft.getClient().getRenderDelegate().getScreenWidth();
		screenHeight = Spoutcraft.getClient().getRenderDelegate().getScreenHeight();
	}

	private int screenHeight, screenWidth;

	@Override
	public double getHeight() {
		return screenHeight > 0 ? screenHeight : 240;
	}

	@Override
	public double getWidth() {
		return screenWidth > 0 ? screenWidth : 427;
	}

	public GenericScreen setBgVisible(boolean enable) {
		bgvis = enable;
		return this;
	}

	public boolean isBgVisible() {
		return bgvis;
	}

	@Override
	public int getNumBytes() {
		return super.getNumBytes() + 1;
	}

	@Override
	public void readData(DataInputStream input) throws IOException {
		super.readData(input);
		setBgVisible(input.readBoolean());
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		super.writeData(output);
		output.writeBoolean(isBgVisible());
	}

	protected boolean canRender(Widget widget) {
		return widget.isVisible();
	}

	RenderPriority[] rvalues = RenderPriority.values();

	public void render() {
		for (RenderPriority priority : rvalues) {
			for (Widget widget : widgets.keySet()) {
				if (widget.getPriority() == priority && canRender(widget)) {
					GL11.glPushMatrix();
					widget.render();
					GL11.glPopMatrix();
				}
			}
		}
	}

	public Screen setMouseX(int mouseX) {
		this.mouseX = mouseX;
		return this;
	}

	public Screen setMouseY(int mouseY) {
		this.mouseY = mouseY;
		return this;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	@Override
	public Widget copy() {
		throw new UnsupportedOperationException("You can not create a copy of a screen");
	}

	public Set<Widget> getAttachedWidgetsAsSet() {
		return getAttachedWidgetsAsSet(false);
	}

	public Set<Widget> getAttachedWidgetsAsSet(boolean recursive) {
		Set<Widget> set = new HashSet<Widget>();
		set.addAll(widgets.keySet());
		if(recursive) {
			for(Widget w:widgets.keySet()) {
				if(w instanceof Screen) {
					set.addAll(((Screen)w).getAttachedWidgetsAsSet(true));
				}
			}
		}
		return set;
	}
}
