package org.spoutcraft.spoutcraftapi.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.spoutcraft.spoutcraftapi.Spoutcraft;
import org.spoutcraft.spoutcraftapi.UnsafeClass;
import org.spoutcraft.spoutcraftapi.io.SpoutInputStream;
import org.spoutcraft.spoutcraftapi.io.SpoutOutputStream;

@UnsafeClass
public class GenericRadioButton extends GenericButton implements RadioButton {
	
	boolean selected = false;
	int group = 0;
	
	public GenericRadioButton() {
		super();
	}
	
	public GenericRadioButton(String text) {
		super(text);
	}

	@Override
	public void readData(SpoutInputStream input) throws IOException {
		super.readData(input);
		selected = input.readBoolean();
		group = input.readInt();
	}

	@Override
	public void writeData(SpoutOutputStream output) throws IOException {
		super.writeData(output);
		output.writeBoolean(selected);
		output.writeInt(group);
	}

	@Override
	public WidgetType getType() {
		return WidgetType.RadioButton;
	}

	@Override
	public void render() {
		Spoutcraft.getClient().getRenderDelegate().render(this);
	}

	public boolean isSelected() {
		return selected;
	}

	public RadioButton setSelected(boolean selected) {
		if(selected) {
			for(RadioButton b:getRadiosInGroup()) {
				b.setSelected(false);
			}
		}
		this.selected = selected;
		return this;
	}

	public int getGroup() {
		return group;
	}

	public RadioButton setGroup(int group) {
		this.group = group;
		return this;
	}
	
	public List<RadioButton> getRadiosInGroup() {
		List<RadioButton> ret = new ArrayList<RadioButton>();
		for(Widget w:getScreen().getAttachedWidgets()) {
			if(w instanceof RadioButton) {
				if(((RadioButton)w).getGroup() == group) {
					ret.add((RadioButton)w);
				}
			}
		}
		return ret;
	}
}
