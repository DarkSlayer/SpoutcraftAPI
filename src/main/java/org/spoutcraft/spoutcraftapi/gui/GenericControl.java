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

import org.spoutcraft.spoutcraftapi.Spoutcraft;
import org.spoutcraft.spoutcraftapi.packet.PacketUtil;

public abstract class GenericControl extends GenericWidget implements Control{

	protected boolean enabled = true;
	protected Color color = new Color(0.878F, 0.878F, 0.878F);
	protected Color disabledColor = new Color(0.625F, 0.625F, 0.625F);
	protected boolean focus = false;
	
	public GenericControl() {
		
	}
	
	@Override
	public int getNumBytes() {
		return super.getNumBytes() + 12;
	}
	
	public int getVersion() {
		return super.getVersion() + 3;
	}

	@Override
	public void readData(DataInputStream input) throws IOException {
		super.readData(input);
		setEnabled(input.readBoolean());
		setColor(PacketUtil.readColor(input));
		setDisabledColor(PacketUtil.readColor(input));
		setFocus(input.readBoolean());
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		super.writeData(output);
		output.writeBoolean(isEnabled());
		PacketUtil.writeColor(output, getColor());
		PacketUtil.writeColor(output, getDisabledColor());
		output.writeBoolean(isFocus());
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public Control setEnabled(boolean enable) {
		enabled = enable;
		return this;
	}

	public Color getColor() {
		return color;
	}

	public Control setColor(Color color) {
		this.color = color;
		return this;
	}

	public Color getDisabledColor() {
		return disabledColor;
	}

	public Control setDisabledColor(Color color) {
		this.disabledColor = color;
		return this;
	}
	
	public boolean isFocus() {
		return focus;
	}
	
	public Control setFocus(boolean focus) {
		if(this.focus != focus) {
			this.focus = focus;
			Spoutcraft.getWidgetManager().sendFocusUpdate(this, focus);
		}
		return this;
	}

	@Override
	public Control copy() {
		return ((Control)super.copy()).setEnabled(isEnabled()).setColor(getColor().clone()).setDisabledColor(getDisabledColor().clone());
	}

	public boolean onKeyPressed(Keyboard key) {
		return false;
	}

	public boolean onKeyReleased(Keyboard key) {
		return false;
	}
	
}
