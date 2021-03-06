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
package org.spoutcraft.spoutcraftapi.entity;

/**
 * Represents a Primed TNT.
 */
public interface TNTPrimed extends Explosive {
	/**
	 * Set the number of ticks until the TNT blows up after being primed.
	 * @param fuseTicks The fuse ticks
	 */
	public void setFuseTicks(int fuseTicks);

	/**
	 * Retrieve the number of ticks until the explosion of this TNTPrimed entity
	 * @return the number of ticks until this TNTPrimed explodes
	 */
	public int getFuseTicks();
}
