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
package org.spoutcraft.spoutcraftapi.event.animation;

import org.spoutcraft.spoutcraftapi.animation.Animation;
import org.spoutcraft.spoutcraftapi.event.HandlerList;

public class AnimationPauseEvent extends AnimationEvent<AnimationPauseEvent> {
	private HandlerList<AnimationPauseEvent> handlers = new HandlerList<AnimationPauseEvent>();

	public AnimationPauseEvent(Animation animation) {
		super(animation);
	}

	@Override
	public HandlerList<AnimationPauseEvent> getHandlers() {
		return handlers;
	}

	@Override
	protected String getEventName() {
		return "AnimationPauseEvent";
	}
}
