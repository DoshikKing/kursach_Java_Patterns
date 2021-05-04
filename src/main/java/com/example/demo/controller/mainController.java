package com.example.demo.controller;

import com.example.demo.components.Record;
import com.example.demo.components.Shop;
import com.example.demo.components.User;
import com.example.demo.service.ShopService;
import com.example.demo.service.RecordService;
import com.example.demo.service.userService;
import com.example.demo.work.WorkFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
public class mainController {
    private boolean flag = false;
    private ShopService shopService;
    private RecordService recordService;
    private com.example.demo.service.userService userService;
    private WorkFlow workFlow;

    @Autowired
    public mainController(com.example.demo.service.RecordService recordService, WorkFlow workFlow, ShopService shopService, userService userService) {
        this.shopService = shopService;
        this.recordService = recordService;
        this.userService = userService;
        this.workFlow = workFlow;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model){
        String username = authentication.getName();
        model.addAttribute("username", username);
        
        String buff = "";
        //buff += workFlow.printShops(shopService.getAllShops());
        buff += workFlow.printRecords(userService.findUser(username).getRecordList());
        model.addAttribute("buff", buff);
        model.addAttribute("currentDate", LocalDate.now().toString());
        model.addAttribute("types", workFlow.typesOfShops(shopService.getAllShops()));
        return "home";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
    @GetMapping("/errorPage")
    public String error(){
        return "errorPage";
    }

    @GetMapping("/home/show")
    public @ResponseBody String show(Model model){
        String buff = "";
        buff += workFlow.printShops(shopService.getAllShops());
        buff += workFlow.printRecords(recordService.getAllRecords());
        model.addAttribute("buff", buff);
        return buff;
    }

    @PostMapping("/home/addBank")
    public String add(@RequestParam String name,
                      @RequestParam String address){
        Shop shop = new Shop();
        shop.setName(name);
        shop.setAddress(address);
        shopService.addShop(shop);
        return "redirect:/home";
    }
    @PostMapping("/home/addRecord")
    public String add(@RequestParam String selectedDate,
                      @RequestParam String selectedTime, String shopName, Authentication authentication, Model model){
        Record record = new Record();
        Shop shop = shopService.getShopById(shopService.getBankId(shopName));
        User user = userService.findUser(authentication.getName());

        for (Record item:recordService.getAllRecords()) {
            if(item.getDate().equals(selectedDate) && item.getTime().equals(selectedTime)){
                return "redirect:/errorPage";
            }
        }

        record.setDate(selectedDate);
        record.setTime(selectedTime);
        record.user = user;
        record.shop = shop;
        recordService.addRecord(record);
        shop.setRecordList(recordService.getAllRecords());
        user.setRecordList(recordService.getAllRecords());
        return "redirect:/home";
    }
    @GetMapping("/home/removeRecord")
    public String removeCards(@RequestParam Long id){
        recordService.deleteRecordById(id);
        return "redirect:/home";
    }
    @GetMapping("/home/removeShop")
    public String removeBanks(@RequestParam Long id){
        shopService.deleteShopById(id);
        return "redirect:/home";
    }
    @GetMapping("/home/getCardBank")
    public @ResponseBody String getCardBank(@RequestParam Long id){
        return "name:" + recordService.getShopByRecord(id).getName() + " address:" + recordService.getShopByRecord(id).getAddress() + " id:" + recordService.getShopByRecord(id).getId();
    }

    @GetMapping("/home/getBankByName")
    public @ResponseBody
    String getBanksByName(){
        return workFlow.printShops(shopService.filterByName());
    }
    @GetMapping("/home/getBankById")
    public @ResponseBody
    String getBanksById(){
        return workFlow.printShops(shopService.filterByShopId());
    }
    @GetMapping("/home/getBankByAddress")
    public @ResponseBody
    String getBanksByAddress(){
        return workFlow.printShops(shopService.filterByAddress());
    }
    @GetMapping("/home/getCardById")
    public @ResponseBody
    String getCardsById(){
        return workFlow.printRecords(recordService.filterByRecordId());
    }
    @GetMapping("/home/getCardByCode")
    public @ResponseBody
    String getCardsByCode(){
        return workFlow.printRecords(recordService.filterByTime());
    }
    @GetMapping("/home/getCardByCardNumber")
    public @ResponseBody
    String getCardsByCardNumber(){
        return workFlow.printRecords(recordService.filterByDate());
    }

}

