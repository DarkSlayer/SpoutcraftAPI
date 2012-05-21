/*
 * This file is part of SpoutcraftAPI.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutcraftAPI is licensed under the GNU Lesser General Public License.
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
package org.spoutcraft.spoutcraftapi.block;

import org.spoutcraft.spoutcraftapi.entity.CreatureType;

public interface CreatureSpawner extends BlockState{
	/**
	 * Get the spawner's creature type.
	 *
	 * @return
	 */
	public CreatureType getCreatureType();

	/**
	 * Set the spawner creature type.
	 *
	 * @param mobType
	 */
	public void setCreatureType(CreatureType creatureType);

	/**
	 * Get the spawner's creature type.
	 *
	 * @return
	 */
	public String getCreatureTypeId();

	/**
	 * Set the spawner mob type.
	 *
	 * @param creatureType
	 */
	public void setCreatureTypeId(String creatureType);

	/**
	 * Get the spawner's delay.
	 *
	 * @return
	 */
	public int getDelay();

	/**
	 * Set the spawner's delay.
	 *
	 * @param delay
	 */
	public void setDelay(int delay);
}
