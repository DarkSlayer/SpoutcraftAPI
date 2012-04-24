package org.spoutcraft.spoutcraftapi.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.spoutcraft.spoutcraftapi.Spoutcraft;
import org.spoutcraft.spoutcraftapi.inventory.ItemStack;

public class GenericSlot extends GenericControl implements Slot {
	private ItemStack stack = new ItemStack(0);
	private int depth = 16;

	public WidgetType getType() {
		return WidgetType.Slot;
	}

	public void render() {
		Spoutcraft.getRenderDelegate().render(this);
	}

	public ItemStack getItem() {
		if(stack == null) {
			stack = new ItemStack(0);
		}
		return stack.clone();
	}

	public Slot setItem(ItemStack item) {
		if(item == null) {
			stack = new ItemStack(0);
			setTooltip("");
			return this;
		}
		stack = item.clone();
		setTooltip(Spoutcraft.getMaterialManager().getToolTip(stack));
		return this;
	}

	public boolean onItemPut(ItemStack item) {
		return true;
	}

	public boolean onItemTake(ItemStack item) {
		return true;
	}

	public void onItemShiftClicked() {
	}

	public boolean onItemExchange(ItemStack current, ItemStack cursor) {
		return true;
	}

	public int getDepth() {
		return depth;
	}

	public Slot setDepth(int depth) {
		this.depth = depth;
		return this;
	}

	@Override
	public void readData(DataInputStream input) throws IOException {
		super.readData(input);
		stack.setTypeId(input.readInt());
		stack.setAmount((int)input.readShort());
		stack.setDurability(input.readShort());
		depth = input.readInt();
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		super.writeData(output);
		output.writeInt(stack.getTypeId());
		output.writeShort((short)stack.getAmount());
		output.writeShort(stack.getDurability());
		output.writeInt(depth);
	}
}
