package com.guru.View;

import com.guru.Controller.Config;
import com.guru.Controller.Helper;
import com.guru.Model.StockCard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class StockCardGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel pnl_add;
    private JTextField fld_code;
    private JLabel lbl_code;
    private JLabel lbl_name;
    private JLabel lbl_type;
    private JLabel lbl_unit;
    private JLabel lbl_barcode;
    private JLabel lbl_vat;
    private JLabel lbl_explanation;
    private JTextField fld_name;
    private JFormattedTextField fld_type;
    private JTextField fld_unit;
    private JTextField fld_barcode;
    private JTextField fld_explanation;
    private JFormattedTextField fld_vat;
    private JButton btn_add;
    private JPanel pnl_other;
    private JButton btn_del;
    private JButton btn_update;
    private JButton btn_copy;
    private JButton btn_list;
    private JButton btn_find;
    private JLabel lbl_title;
    private JPanel pnl_out;
    private JTextField fld_delCode;
    private JTextField fld_otherUpdCode;
    private JTextField fld_copyCode;
    private JTextField fld_findCode;
    private JTextField fld_oldCopyCode;
    private DefaultTableModel mdl_user_list;
    private JTable tbl_user_list;

    private final StockCard stockCard;

    public StockCardGUI(StockCard stockCard) {
        this.stockCard = stockCard;
        add(wrapper);
        setSize(1000,500);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);

        DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(formatter);
        fld_type.setFormatterFactory(formatterFactory);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        NumberFormatter doubleFormatter = new NumberFormatter(decimalFormat);
        doubleFormatter.setValueClass(Double.class);
        doubleFormatter.setMinimum(0.0);
        DefaultFormatterFactory doubleFormatterFactory = new DefaultFormatterFactory(doubleFormatter);
        fld_vat.setFormatterFactory(doubleFormatterFactory);


        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fld_code.getText().isEmpty()){
                    Helper.showMessage("fill");
                }else{
                    Double vatText = Double.parseDouble(fld_vat.getText().replace(",", ".")); // Virgülü noktaya çevir
                    stockCard.setStock_code(fld_code.getText());
                    stockCard.setStock_name(fld_name.getText());
                    stockCard.setStock_type(Integer.parseInt(fld_type.getText()));
                    stockCard.setUnit(fld_unit.getText());
                    stockCard.setBarcode(fld_barcode.getText());
                    stockCard.setVat_type(vatText);
                    //stockCard.setVat_type(Double.parseDouble(fld_vat.getText()));
                    stockCard.setExplanation(fld_explanation.getText());
                    if(StockCard.add(stockCard)){
                            Helper.showMessage("done");
                            fld_code.setText(null);
                            fld_name.setText(null);
                            fld_type.setText(null);
                            fld_unit.setText(null);
                            fld_barcode.setText(null);
                            fld_vat.setText(null);
                            fld_explanation.setText(null);
                    }else{
                            Helper.showMessage("error");
                    }
                }
            }
        });



        btn_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListGUI listGUI = new ListGUI(stockCard);
            }
        });

        btn_del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fld_delCode.getText().isEmpty()){
                    Helper.showMessage("fill");
                }else{
                    if(StockCard.delete(fld_delCode.getText())){
                        Helper.showMessage("done");
                        fld_delCode.setText(null);
                    }else{
                        Helper.showMessage("error");
                        fld_delCode.setText(null);
                    }
                }
            }
        });

        btn_find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fld_findCode.getText().isEmpty()){
                    Helper.showMessage("Lütfen Stok Kodu alanını doldurunuz.");
                }else{
                    if(StockCard.getFetchByCode(fld_findCode.getText()) == null){
                        Helper.showMessage("Girilen Stok Kodu bulunamadı.");
                        fld_findCode.setText(null);
                    }else{
                        FindGUI findGUI = new FindGUI(StockCard.getFetchByCode(fld_findCode.getText()));
                    }
                }

            }
        });

        btn_copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fld_copyCode.getText().isEmpty() || fld_oldCopyCode.getText().isEmpty()){
                    Helper.showMessage("Lüften ilgili alanı doldurunuz.");
                }else{
                    if(fld_oldCopyCode.getText().equals(fld_copyCode.getText())){
                        Helper.showMessage("İki değer birbirine eşit olamaz!");
                    }
                    else{
                        if(StockCard.copy(fld_oldCopyCode.getText(),fld_copyCode.getText())){
                            Helper.showMessage("done");
                            fld_oldCopyCode.setText(null);
                            fld_copyCode.setText(null);
                        }else{
                            Helper.showMessage("error");
                        }
                    }
                }
            }
        });
    }

    public static void main(String[] args){
        Helper.setLayout();
        StockCard sc = new StockCard();
        StockCardGUI opGUI = new StockCardGUI(sc);
    }

}
