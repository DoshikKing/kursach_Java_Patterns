package com.example.demo.work;

import com.example.demo.components.Shop;
import com.example.demo.components.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkFlow {
    public String printShops(List<Shop> list){
        String buff = "<h style='color: white'>Барбрешопы</h><table style='color: white' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Название</th>" +
                "      <th scope='col'>Адрес</th>" +
                "      <th scope='col'>#</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        int i = 0;
        for (Shop item: list) {
            buff += "<tr><td>" + item.getName() + "</td><td>" + item.getAddress() + "</td><td><a href='home/removeShop?id=" + item.getId() + "'>Delete</a></td></tr>";
            i++;
        }
        if (i == 0)
        {
            buff+= "<tr><td>Нет барбершопов</td></tr>";
        }
        i = 0;
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
    public String printRecords(List<Record> list){
        int i = 0;
        String buff = "<table style='color: white; text-align: center' class='table'> <thead>" +
                "    <tr>" +
                "      <th scope='col'>Время</th>" +
                "      <th scope='col'>Дата</th>" +
                "      <th scope='col'>#</th>" +
                "    </tr>" +
                "  </thead>" +
                "<tbody>";
        for (Record item: list) {
            buff += "<tr><td>" + item.getTime() + "</td><td>" + item.getDate() + "</td><td><a href='home/removeRecord?id=" + item.getId() + "'>Delete</a></td></tr>";
            i++;
        }
        if (i == 0)
        {
            buff+= "<tr><td>Нет</td><td>новых</td><td>записей</td></tr>";
        }
        buff += "</tbody>" +
                "</table>";
        return buff;
    }
    public String typesOfShops(List<Shop> list){
        String buff = "";
        for (Shop item: list) {
            buff += "<option value='" + item.getName() + "'>" + item.getName() + "</option>";
        }
        return buff;
    }
}
