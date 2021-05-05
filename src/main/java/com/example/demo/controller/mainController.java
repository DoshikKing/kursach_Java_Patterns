package com.example.demo.controller;

import com.example.demo.components.Record;
import com.example.demo.components.Shop;
import com.example.demo.components.User;
import com.example.demo.service.RoleService;
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
    private RoleService roleService;
    private com.example.demo.service.userService userService;
    private WorkFlow workFlow;

    @Autowired
    public mainController(com.example.demo.service.RecordService recordService, WorkFlow workFlow, ShopService shopService, userService userService, RoleService roleService) {
        this.shopService = shopService;
        this.roleService = roleService;
        this.recordService = recordService;
        this.userService = userService;
        this.workFlow = workFlow;
    }

    @GetMapping("/forward")
    public String forward(Authentication authentication, Model model){
        if(userService.findUser(authentication.getName()).getRoleList().contains(roleService.findByRole("USER"))) {
            model.addAttribute("content", "2, /home");
        }
        if(userService.findUser(authentication.getName()).getRoleList().contains(roleService.findByRole("ADMIN"))) {
            model.addAttribute("content", "5, /admin");
        }
        if(userService.findUser(authentication.getName()).getRoleList().contains(roleService.findByRole("STAFF"))) {
            model.addAttribute("content", "5, /staff");
        }
        return "forward";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/staff")
    public String staff(Model model){
        String buff = "";
        buff += workFlow.printRecordsStaff(recordService.getAllRecords());
        model.addAttribute("buff", buff);
        return "staff";
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model){
        String username = authentication.getName();
        model.addAttribute("username", username);
        String buff = "";
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
    @GetMapping("/shopErrorPage")
    public String errorShop(){
        return "shopErrorPage";
    }

    @GetMapping("/home/show")
    public @ResponseBody String show(){
        String buff = "";
        buff += workFlow.printShopsAdmin(shopService.getAllShops());
        buff += workFlow.printRecordsAdmin(recordService.getAllRecords());
        return buff;
    }

    @PostMapping("/home/addShop")
    public String add(@RequestParam String name,
                      @RequestParam String address){

        for (Shop item:shopService.getAllShops()) {
            if(item.getName().equals(name) && item.getAddress().equals(address)){
                return "redirect:/shopErrorPage";
            }
        }

        Shop shop = new Shop();
        shop.setName(name);
        shop.setAddress(address);
        shopService.addShop(shop);
        return "redirect:/forward";
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
        return "redirect:/forward";
    }
    @GetMapping("/home/removeRecord")
    public String removeCards(@RequestParam Long id){
        recordService.deleteRecordById(id);
        return "redirect:/forward";
    }
    @GetMapping("/home/removeShop")
    public String removeBanks(@RequestParam Long id){
        shopService.deleteShopById(id);
        return "redirect:/forward";
    }
    @GetMapping("/home/getRecordShop")
    public @ResponseBody String getRecordShop(@RequestParam Long id){
        return "name:" + recordService.getShopByRecord(id).getName() + " address:" + recordService.getShopByRecord(id).getAddress() + " id:" + recordService.getShopByRecord(id).getId();
    }

    @GetMapping("/home/getShopByName")
    public @ResponseBody
    String getShopByName(){
        return workFlow.printShopsAdmin(shopService.filterByName());
    }
    @GetMapping("/home/getShopById")
    public @ResponseBody
    String getShopById(){
        return workFlow.printShopsAdmin(shopService.filterByShopId());
    }
    @GetMapping("/home/getShopByAddress")
    public @ResponseBody
    String getShopByAddress(){
        return workFlow.printShopsAdmin(shopService.filterByAddress());
    }
    @GetMapping("/home/getRecordById")
    public @ResponseBody
    String getRecordById(){
        return workFlow.printRecordsAdmin(recordService.filterByRecordId());
    }
    @GetMapping("/home/getRecordByTime")
    public @ResponseBody
    String getRecordByTime(){
        return workFlow.printRecordsAdmin(recordService.filterByTime());
    }
    @GetMapping("/home/getRecordByDate")
    public @ResponseBody
    String getRecordByDate(){
        return workFlow.printRecordsAdmin(recordService.filterByDate());
    }

}

