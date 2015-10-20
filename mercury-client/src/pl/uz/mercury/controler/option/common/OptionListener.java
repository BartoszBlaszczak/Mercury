package pl.uz.mercury.controler.option.common;

import javax.swing.event.TableModelEvent;

public interface OptionListener
{
	void onAdd();
	void onUpdate();
	void onDelete();
	void onRefresh();
	void onChange(TableModelEvent e);
}
