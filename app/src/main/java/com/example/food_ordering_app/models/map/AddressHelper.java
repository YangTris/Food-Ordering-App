package com.example.food_ordering_app.models.map;

import java.util.ArrayList;
import java.util.HashMap;

public class AddressHelper {
    private HashMap<String, ArrayList<String>> cityDistrictsMap;
    private HashMap<String, ArrayList<String>> districtWardsMap;

    public AddressHelper() {
        this.cityDistrictsMap = new HashMap<>();
        this.districtWardsMap = new HashMap<>();
        // Đổ dữ liệu vào sẵn
        initializeDistricts();
        initializeWards();
    }

    private void initializeDistricts() {
        addCity("Hồ Chí Minh");
        for (int i = 1; i <= 12; i++) {
            if (i == 2 || i == 9) {
                continue;
            }
            addDistrict("Hồ Chí Minh", "Quận " + i);
        }
        addDistrict("Hồ Chí Minh", "Quận Bình Tân");
        addDistrict("Hồ Chí Minh", "Quận Bình Thạnh");
        addDistrict("Hồ Chí Minh", "Quận Gò Vấp");
        addDistrict("Hồ Chí Minh", "Quận Phú Nhuận");
        addDistrict("Hồ Chí Minh", "Quận Tân Bình");
        addDistrict("Hồ Chí Minh", "Quận Tân Phú");

        addCity("Hà Nội (Coming soon)");
    }

    private void initializeWards() {
        // Thêm phường cho quận 1
        addWard("Quận 1", "Phường Bến Nghé");
        addWard("Quận 1", "Phường Bến Thành");
        addWard("Quận 1", "Phường Cô Giang");
        addWard("Quận 1", "Phường Cầu Kho");
        addWard("Quận 1", "Phường Cầu Ông Lãnh");
        addWard("Quận 1", "Phường Nguyễn Cư Trinh");
        addWard("Quận 1", "Phường Nguyễn Thái Bình");
        addWard("Quận 1", "Phường Phạm Ngũ Lão");
        addWard("Quận 1", "Phường Tân Định");
        addWard("Quận 1", "Phường Đa Kao");

        // Thêm phường cho quận 3
        for (int i = 1; i <= 14; i++) {
            if (i == 6 || i == 7 || i == 8) {
                continue;
            }
            addWard("Quận 3", "Phường " + i);
        }
        addWard("Quận 3", "Phường Võ Thị Sáu");

        // Thêm phường cho quận 4
        for (int i = 1; i <= 18; i++) {
            if (i == 5 || i == 7 || i == 11 || i == 12 || i == 17) {
                continue;
            }
            addWard("Quận 4", "Phường " + i);
        }

        // Thêm phường cho quận 5
        for (int i = 1; i <= 14; i++) {
            addWard("Quận 5", "Phường " + i);
        }

        // Thêm phường cho quận 6
        for (int i = 1; i <= 14; i++) {
            addWard("Quận 6", "Phường " + i);
        }

        // Thêm phường cho quận 7
        addWard("Quận 7", "Phường Bình Thuận");
        addWard("Quận 7", "Phường Phú Mỹ");
        addWard("Quận 7", "Phường Phú Thuận");
        addWard("Quận 7", "Phường Tân Hưng");
        addWard("Quận 7", "Phường Tân Kiểng");
        addWard("Quận 7", "Phường Tân Phong");
        addWard("Quận 7", "Phường Tân Phú");
        addWard("Quận 7", "Phường Tân Quy");
        addWard("Quận 7", "Phường Tân Thuận Đông");
        addWard("Quận 7", "Phường Tân Thuận Tây");

        // Thêm phường cho quận 8
        for (int i = 1; i <= 16; i++) {
            addWard("Quận 8", "Phường " + i);
        }

        // Thêm phường cho quận 10
        for (int i = 1; i <= 15; i++) {
            if (i == 3) {
                continue;
            }
            addWard("Quận 10", "Phường " + i);
        }

        // Thêm phường cho quận 11
        for (int i = 1; i <= 16; i++) {
            addWard("Quận 11", "Phường " + i);
        }

        // Thêm phường cho quận 12
        addWard("Quận 12", "Phường An Phú Đông");
        addWard("Quận 12", "Phường Đông Hưng Thuận");
        addWard("Quận 12", "Phường Hiệp Thành");
        addWard("Quận 12", "Phường Tân Chánh Hiệp");
        addWard("Quận 12", "Phường Tân Hưng Thuận");
        addWard("Quận 12", "Phường Tân Thới Hiệp");
        addWard("Quận 12", "Phường Tân Thới Nhất");
        addWard("Quận 12", "Phường Thạnh Lộc");
        addWard("Quận 12", "Phường Thạnh Xuân");
        addWard("Quận 12", "Phường Thới An");
        addWard("Quận 12", "Phường Trung Mỹ Tây");

        // Thêm phường cho quận Bình Tân
        addWard("Quận Bình Tân", "Phường An Lạc");
        addWard("Quận Bình Tân", "Phường An Lạc A");
        addWard("Quận Bình Tân", "Phường Bình Hưng Hòa");
        addWard("Quận Bình Tân", "Phường Bình Hưng Hòa A");
        addWard("Quận Bình Tân", "Phường Bình Hưng Hòa B");
        addWard("Quận Bình Tân", "Phường Bình Trị Đông");
        addWard("Quận Bình Tân", "Phường Bình Trị Đông A");
        addWard("Quận Bình Tân", "Phường Bình Trị Đông B");
        addWard("Quận Bình Tân", "Phường Tân Tạo");
        addWard("Quận Bình Tân", "Phường Tân Tạo A");

        // Thêm phường cho quận Bình Thạnh
        // 1, 2, 3, 5, 6, 7, 11, 12, 13, 14, 15, 17, 19, 21, 22, 24, 25, 26, 27, 28
        for (int i = 1; i <= 28; i++) {
            if (i == 4 || i == 8 || i == 9 || i == 10 || i == 16 || i == 18 || i == 20 || i == 23) {
                continue;
            }
            addWard("Quận Bình Thạnh", "Phường " + i);
        }

        // Thêm phường cho quận Gò Vấp
        // 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17
        for (int i = 1; i <= 17; i++) {
            if (i == 2) {
                continue;
            }
            addWard("Quận Gò Vấp", "Phường " + i);
        }
    }

    // Thêm một thành phố vào map
    public void addCity(String city) {
        cityDistrictsMap.put(city, new ArrayList<>());
    }

    // Thêm một quận vào map của thành phố
    public void addDistrict(String city, String district) {
        if (cityDistrictsMap.containsKey(city)) {
            cityDistrictsMap.get(city).add(district);
            districtWardsMap.put(district, new ArrayList<>());
        }
    }

    // Thêm một phường vào map của quận
    public void addWard(String district, String ward) {
        if (districtWardsMap.containsKey(district)) {
            districtWardsMap.get(district).add(ward);
        }
    }

    // Lấy danh sách quận của một thành phố
    public ArrayList<String> getDistrictsByCity(String city) {
        return cityDistrictsMap.get(city);
    }

    // Lấy danh sách phường của một quận
    public ArrayList<String> getWardsByDistrict(String district) {
        return districtWardsMap.get(district);
    }

    public ArrayList<String> getCities() {
        return new ArrayList<>(cityDistrictsMap.keySet());
    }
}