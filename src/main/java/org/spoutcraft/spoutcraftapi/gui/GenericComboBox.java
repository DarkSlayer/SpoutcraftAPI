package org.spoutcraft.spoutcraftapi.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.spoutcraft.spoutcraftapi.Spoutcraft;
import org.spoutcraft.spoutcraftapi.addon.Addon;
import org.spoutcraft.spoutcraftapi.event.screen.ButtonClickEvent;

public class GenericComboBox extends GenericButton implements ComboBox {
	
	private List<String> items = new ArrayList<String>();
	private ComboBoxModel model;
	private GenericListView view;
	private Screen screen;
	
	public GenericComboBox() {
		model = new ComboBoxModel();
		view = new ComboBoxView(model, this);
		view.setSelection(0);
		view.setVisible(false);
	}
	
	public GenericComboBox(ComboBoxModel model) {
		this.model = model;
		view = new ComboBoxView(model, this);
		view.setSelection(0);
		view.setVisible(false);
	}
	
	public WidgetType getType() {
		return WidgetType.ComboBox;
	}

	public void render() {
		setAlign(WidgetAnchor.TOP_LEFT);
		Spoutcraft.getRenderDelegate().render(this);
	}

	public ComboBox setItems(List<String> items) {
		this.items = items;
		model.setList(items);
		setSelection(0);
		return this;
	}

	public List<String> getItems() {
		return Collections.unmodifiableList(items);
	}

	public ComboBox openList() {

		screen = getScreen();
		while (!(screen instanceof GenericScreen)) {
			if(screen.getScreen() != null)
				screen = screen.getScreen();
			else 
				break;
		}
		
		screen.attachWidget(getAddon(), view);
		view.setVisible(true);
		view.setPriority(RenderPriority.Lowest); //Makes it the top-most widget
		view.setFocus(true);
		view.setSelection(view.getSelectedRow());
		return this;
	}

	public ComboBox closeList() {
		view.setVisible(false);
		screen.removeWidget(view);
		return this;
	}
	
	public boolean isOpen() {
		return view.isVisible();
	}
	
	@Override
	public Button setText(String text) {return this;}

	public String getSelectedItem() {
		ComboBoxItem item = model.getItem(view.getSelectedRow());
		return item == null ? "" : item.getText();
	}

	public int getSelectedRow() {
		return view.getSelectedRow();
	}
	
	public void onSelectionChanged(int i, String text) {}

	@Override
	public String getText() {
		return getSelectedItem();
	}
	
	@Override
	public void onButtonClick(ButtonClickEvent event) {
		super.onButtonClick(event);
		if(!isOpen()) {
			openList();
		} else {
			closeList();
		}
	}


	public ComboBox setSelection(int row) {
		view.setSelection(row);
		return this;
	}
	
	@Override
	public Widget setScreen(Addon addon, Screen screen) {
		
		super.setScreen(addon, screen);
		
		screen = getScreen();
		while (!(screen instanceof GenericScreen)) {
			if(screen.getScreen() != null)
				screen = screen.getScreen();
			else 
				break;
		}
		
		screen.attachWidget(addon, view);
		
		return this;
	}
	
	@Override
	public Widget setScreen(Screen screen) {
		return setScreen(null, screen);
	}

	protected class ComboBoxModel extends AbstractListModel {
		List<String> list = new ArrayList<String>();
		List<ComboBoxItem> items = new ArrayList<GenericComboBox.ComboBoxItem>();
		
		public void setList(List<String> list) {
			this.list = list;
			updateItems();
		}
		
		private void updateItems() {
			items.clear();
			for(String l:list) {
				ComboBoxItem item = new ComboBoxItem(this);
				item.setTitle(l);
				items.add(item);
			}
		}

		@Override
		public ComboBoxItem getItem(int row) {
			if(row < 0 || row >= getSize()) return null;
			return items.get(row);
		}

		@Override
		public int getSize() {
			return items.size();
		}

		@Override
		public void onSelected(int item, boolean doubleClick) {
		}

		public void handleClick(ComboBoxItem comboBoxItem) {
			int i = items.indexOf(comboBoxItem);
			onSelectionChanged(i, getItem(i).getText());
			closeList();
		}

		public boolean isLast(ComboBoxItem comboBoxItem) {
			return items.indexOf(comboBoxItem) == items.size() - 1;
		}
	}
	
	public class ComboBoxItem implements ListWidgetItem {
		private String text;
		private ListWidget widget;
		private ComboBoxModel model;
		private GenericGradient gradient = new GenericGradient();
		
		public ComboBoxItem(ComboBoxModel model) {
			this.model = model;
		}
		
		public void setListWidget(ListWidget widget) {
			this.widget = widget;
		}

		public ListWidget getListWidget() {
			return widget;
		}

		public int getHeight() {
			return 11;
		}

		public void render(int x, int y, int width, int height) {
			Spoutcraft.getRenderDelegate().getMinecraftFont().drawString(text, x+2, y+2, 0xffffffff);
			if(!model.isLast(this)) {
				gradient.setX(x).setY(y+11).setWidth(width).setHeight(1);
				gradient.setTopColor(new Color(0f,0f,0f,1f)).setBottomColor(new Color(1f,1f,1f,1f));
				gradient.setOrientation(Orientation.HORIZONTAL); //Doesn't work yet :(
				Spoutcraft.getRenderDelegate().render(gradient);
			}
		}
		
		public void onClick(int x, int y, boolean doubleClick) {
			if(x != -1) {
				model.handleClick(this);
			} else if(doubleClick) {
				model.handleClick(this);
			}
		}

		public String getText() {
			return text;
		}

		public void setTitle(String title) {
			this.text = title;
		}
	}
	
	protected class ComboBoxView extends GenericListView {
		
		ComboBox combo;
		public ComboBoxView(AbstractListModel model, ComboBox box) {
			super(model);
			combo = box;
			setBackgroundColor(new Color(0.5f,0.5f,0.5f,0.9f));
		}

		@Override
		public double getWidth() {
			setWidth((int) combo.getWidth());
			return combo.getWidth();
		}
		
		@Override
		public double getHeight() {
			int a = getAvailableHeight(false) - 5;
			if(a < 30) {
				a = getAvailableHeight(true) - 5;
			}
			double res = Math.min(a, getInnerSize(Orientation.VERTICAL));
			setHeight((int) res);
			return res;
		}
		
		@Override
		public int getX() {
			return (int) combo.getActualX();
		}
		
		@Override
		public int getY() {
			int h = (int) getHeight();
			int a = getAvailableHeight(false);
			if(a < 30) {
				return (int) (combo.getActualY()-h);
			} else {
				return (int) (combo.getActualY() + combo.getHeight());
			}
		}

		@Override
		public double getActualX() {
			setX(getX());
			return super.getActualX();
		}

		@Override
		public double getActualY() {
			setY(getY());
			return super.getActualY();
		}

		protected int getAvailableHeight(boolean top) {
			if(!top) {
				return (int) (Spoutcraft.getClient().getRenderDelegate().getScreenHeight() - combo.getActualY() - combo.getHeight() - 5);
			} else {
				return (int) (combo.getActualY() - 5);
			}
		}
	}
}
