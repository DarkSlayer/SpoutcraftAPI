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
package org.spoutcraft.spoutcraftapi.addon;

public interface AddonStore {
	public void downloadAddon(int databaseId, DownloadEventDelegate delegate);
	public void downloadAddon(String name, DownloadEventDelegate delegate);
	public boolean hasUpdate(Addon addon);
	public boolean hasInternetAccess(Addon addon);
	public long getQuota(Addon addon);
	public boolean isEnabled(Addon addon);

	public abstract class DownloadEventDelegate {
		public abstract void onDownloadFinished(Addon addon);
		public abstract void onDownloadFailure(Exception e, int databaseId, String name);
	}
}
