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

import java.io.IOException;

import org.spoutcraft.spoutcraftapi.Spoutcraft;
import org.spoutcraft.spoutcraftapi.UnsafeClass;
import org.spoutcraft.spoutcraftapi.io.SpoutInputStream;
import org.spoutcraft.spoutcraftapi.io.SpoutOutputStream;

@UnsafeClass
public class GenericEntityWidget extends GenericWidget implements EntityWidget {
	private int entityId = 0;

	public GenericEntityWidget() {

	}

	public GenericEntityWidget(int entityId) {
		this.entityId = entityId;
	}

	public WidgetType getType() {
		return WidgetType.EntityWidget;
	}

	public EntityWidget setEntityId(int id) {
		entityId = id;
		return this;
	}

	public int getEntityId() {
		return entityId;
	}
	
	@Override
	public void readData(SpoutInputStream input) throws IOException {
		super.readData(input);
		entityId = input.readInt();
	}

	@Override
	public void writeData(SpoutOutputStream output) throws IOException {
		super.writeData(output);
		output.writeInt(entityId);
	}
	
	@Override
	public EntityWidget copy() {
		return ((EntityWidget)super.copy()).setEntityId(getEntityId());
	}

	public void render() {
		Spoutcraft.getClient().getRenderDelegate().render(this);
	}
}
