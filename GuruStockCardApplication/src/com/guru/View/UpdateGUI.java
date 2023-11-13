package com.guru.View;

import com.guru.Controller.Config;
import com.guru.Controller.Helper;
import com.guru.Model.StockCard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UpdateGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JPanel pnl_stockCard;
    private JTable tbl_stockCard;
    private JScrollPane scrl_stokCard;
    private JButton btn_updGUI;
    private JTextField fld_updCode;
    private JButton btn_update;
    private final StockCard stockCard;
    private DefaultTableModel mdl_stock_list;
    private Object[] row_user_list;


    public UpdateGUI(StockCard stockCard){
        this.stockCard = stockCard;
        add(wrapper);
        setSize(1000,500);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        mdl_stock_list = new DefaultTableModel();
        Object[] col_stock_list = {"ID","Stok Kodu","Stok Adı","Stok Tipi","Birimi","Barkodu","KDV Tipi",
                "Açıklama","Oluşturma Zamanı"};
        mdl_stock_list.setColumnIdentifiers(col_stock_list);

        for(StockCard obj : StockCard.getFetch(stockCard.getStock_code())){
            Object[] row = new Object[col_stock_list.length];
            row[0] = obj.getId();
            row[1] = obj.getStock_code();
            row[2] = obj.getStock_name();
            row[3] = obj.getStock_type();
            row[4] = obj.getUnit();
            row[5] = obj.getBarcode();
            row[6] = obj.getVat_type();
            row[7] = obj.getExplanation();
            mdl_stock_list.addRow(row);
        }

        tbl_stockCard.setModel(mdl_stock_list);
        tbl_stockCard.getTableHeader().setReorderingAllowed(false);
    }

    public static void main(String[] args){
        StockCard sc = new StockCard();
        UpdateGUI upd = new UpdateGUI(sc);

    }

}
