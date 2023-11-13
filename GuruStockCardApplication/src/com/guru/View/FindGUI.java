package com.guru.View;

import com.guru.Controller.Config;
import com.guru.Controller.Helper;
import com.guru.Model.StockCard;

import javax.swing.*;

public class FindGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JLabel lbl_stockID;
    private JLabel lbl_stockCode;
    private JTextField fld_id;
    private JTextField fld_code;
    private JTextField fld_name;
    private JTextField fld_type;
    private JTextField fld_unit;
    private JTextField fld_barcode;
    private JTextField fld_vat;
    private JTextField fld_exp;
    private JTextField fld_date;
    private JLabel lbl_stockName;
    private JLabel lbl_stockType;
    private JLabel lbl_unit;
    private JLabel lbl_barcode;
    private JLabel lbl_vat;

    private final StockCard stockCard;


    public FindGUI(StockCard stockCard) {
        this.stockCard = stockCard;
        add(wrapper);
        setSize(1000,500);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_id.setText(String.valueOf(stockCard.getId()));
        fld_code.setText(stockCard.getStock_code());
        fld_name.setText(stockCard.getStock_name());
        fld_type.setText(String.valueOf(stockCard.getStock_type()));
        fld_unit.setText(stockCard.getUnit());
        fld_barcode.setText(stockCard.getBarcode());
        fld_vat.setText(String.valueOf(stockCard.getVat_type()));
        fld_exp.setText(stockCard.getExplanation());
        fld_date.setText(String.valueOf(stockCard.getCreation_date()));
    }

    public static void main (String[] args){
        Helper.setLayout();
        StockCard sc = new StockCard();
        FindGUI findGUI = new FindGUI(sc);
    }
}
