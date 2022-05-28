package com.view;

import com.helper.Config;
import com.helper.Helper;
import com.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JTabbedPane tab_admin;
    private JPanel pnl_hotel_list;
    private JScrollPane scrl_hotel_list;
    private JTable tbl_hotel_list;
    private JPanel pnl_hotel_top;
    private JButton btn_hotel_add;
    private JScrollPane scrl_hotel_list_left;
    private JTable tbl_hotel_type;
    private JPanel pnl_room_list;
    private JPanel pnl_hotel_bottom;
    private JScrollPane scrl_hotel_list_right;
    private JTable tbl_hotel_season;
    private JPanel pnl_room_top;
    private JPanel pnl_bottom;
    private JScrollPane scrl_room_list;
    private JTable tbl_room_list;
    private JScrollPane scrl_room_property;
    private JTable tbl_room_property;
    private JTable tbl_room_price;
    private JButton btn_room_add;
    private JPanel room_sh_form;
    private JTextField fld_region_hotelName;
    private JTextField fld_chec_in;
    private JTextField fld_check_out;
    private JTextField fld_adult_numb;
    private JTextField fld_child_numb;
    private JButton btn_room_sh;

    DefaultTableModel mdl_hotel_list;
    private Object[] row_hotel_list;

    DefaultTableModel mdl_hotel_type;
    private Object[] row_hotel_type;
    private int select_hotel_id;

    DefaultTableModel mdl_hotel_season;
    private Object[] row_hotel_season;

    DefaultTableModel mdl_room_list;
    private Object[] row_room_list;

    DefaultTableModel mdl_room_properties;
    private Object[] row_room_properties;
    private int select_room_id;


    private final Admin admin;

    public AdminGUI(Admin admin) {
        this.admin = admin;
        add(wrapper);
        setSize(1200, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        mdl_hotel_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_hotel_list = {"id", "Hotel Adı", "Yıldız", "Tesis Özellikleri", "Adres", "Telefon", "E-mail"};
        mdl_hotel_list.setColumnIdentifiers(col_hotel_list);
        row_hotel_list = new Object[col_hotel_list.length];
        loadHotelModel();
        tbl_hotel_list.setModel(mdl_hotel_list);
        tbl_hotel_list.getTableHeader().setReorderingAllowed(false);
        tbl_hotel_list.getColumnModel().getColumn(0).setMaxWidth(75);


        tbl_hotel_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                select_hotel_id = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString());
            } catch (Exception ex) {

            }
            loadHotelTypeModel(select_hotel_id);
            loadHotelSeasonModel(select_hotel_id);
            select_hotel_id = 0;
        });

        mdl_hotel_type = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_hotel_type = {"Pansiyon Tipleri"};
        mdl_hotel_type.setColumnIdentifiers(col_hotel_type);
        row_hotel_type = new Object[col_hotel_type.length];
        //loadHotelTypeModel();
        tbl_hotel_type.setModel(mdl_hotel_type);
        tbl_hotel_type.getTableHeader().setReorderingAllowed(false);

        mdl_hotel_season = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_hotel_season = {"Dönem Başlangıcı", "Dönem Bitişi"};
        mdl_hotel_season.setColumnIdentifiers(col_hotel_season);
        row_hotel_season = new Object[col_hotel_season.length];
        //loadHotelSeasonModel();
        tbl_hotel_season.setModel(mdl_hotel_season);
        tbl_hotel_season.getTableHeader().setReorderingAllowed(false);

        mdl_room_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_room_list = {"id", "Hotel Adı", "Oda Tipi", "Stok", "Sezon Tarihleri", "Yetişkin Fiyatı", "Çocuk Fiyatı", "Pansiyon Tipi"};
        mdl_room_list.setColumnIdentifiers(col_room_list);
        row_room_list = new Object[col_room_list.length];
        loadRoomListModel();
        tbl_room_list.setModel(mdl_room_list);
        tbl_room_list.getTableHeader().setReorderingAllowed(false);
        tbl_room_list.getColumnModel().getColumn(0).setMaxWidth(75);

        mdl_room_properties = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_room_properties = {"Oda Özellikleri", "Yatak Bilgisi", "Alan(m2)"};
        mdl_room_properties.setColumnIdentifiers(col_room_properties);
        row_room_properties = new Object[col_room_properties.length];
        //loadRoomPropertiesModel();
        tbl_room_property.setModel(mdl_room_properties);
        tbl_room_property.getTableHeader().setReorderingAllowed(false);

        tbl_room_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                select_room_id = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(), 0).toString());
            } catch (Exception ex) {

            }
            loadRoomPropertiesModel(select_room_id);

            select_room_id = 0;
        });

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI logGUI = new LoginGUI();

        });

        btn_hotel_add.addActionListener(e -> {
            HotelAddGUI hotelAdd = new HotelAddGUI(admin);
            hotelAdd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelModel();
                    tbl_hotel_list.getSelectionModel().clearSelection();
                }
            });
        });

        btn_room_add.addActionListener(e -> {
            RoomAddGUI roomAddGUI = new RoomAddGUI(admin);
            roomAddGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomListModel();
                    tbl_room_list.getSelectionModel().clearSelection();
                    super.windowClosed(e);
                }
            });
        });


        btn_room_sh.addActionListener(e -> {
            String regionHotelName = fld_region_hotelName.getText();

        });
    }


    private void loadHotelModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Hotel obj : Hotel.getList()) {
            i = 0;
            row_hotel_list[i++] = obj.getId();
            row_hotel_list[i++] = obj.getName();
            row_hotel_list[i++] = obj.getStar();
            row_hotel_list[i++] = obj.getProperty();
            row_hotel_list[i++] = obj.getAddress();
            row_hotel_list[i++] = obj.getPhone();
            row_hotel_list[i++] = obj.getEmail();
            mdl_hotel_list.addRow(row_hotel_list);
        }
    }

    private void loadHotelTypeModel(int id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_type.getModel();
        clearModel.setRowCount(0);
        int i;
        for (HotelType obj : HotelType.getListByHotelID(id)) {
            i = 0;
            row_hotel_type[i++] = obj.getType();
            mdl_hotel_type.addRow(row_hotel_type);
        }
    }

    private void loadHotelSeasonModel(int id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_season.getModel();
        clearModel.setRowCount(0);
        int i;
        for (HotelSeason obj : HotelSeason.getListByHotelID(id)) {
            i = 0;
            row_hotel_season[i++] = obj.getSeason_start();
            row_hotel_season[i++] = obj.getSeason_end();
            mdl_hotel_season.addRow(row_hotel_season);
        }
    }

    private void loadRoomListModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Room obj : Room.getList()) {
            i = 0;
            row_room_list[i++] = obj.getId();
            row_room_list[i++] = Hotel.getFetch(obj.getHotel_id()).getName();
            row_room_list[i++] = obj.getRoom_type();
            row_room_list[i++] = obj.getStock();
            row_room_list[i++] = HotelSeason.getFetch(obj.getSeason_id()).getSeason_start() + " - " + HotelSeason.getFetch(obj.getSeason_id()).getSeason_end();
            row_room_list[i++] = obj.getAdult_price();
            row_room_list[i++] = obj.getChild_price();
            row_room_list[i++] = HotelType.getFetch(obj.getHotel_type_id()).getType();
            mdl_room_list.addRow(row_room_list);
        }
    }

    private void loadRoomPropertiesModel(int id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_property.getModel();
        clearModel.setRowCount(0);
        int i;
        for (RoomProperties obj : RoomProperties.getListByRoomID(id)) {
            i = 0;
            row_room_properties[i++] = obj.getProperty();
            row_room_properties[i++] = obj.getBed();
            row_room_properties[i++] = obj.getArea();
            mdl_room_properties.addRow(row_room_properties);
        }
    }


}
