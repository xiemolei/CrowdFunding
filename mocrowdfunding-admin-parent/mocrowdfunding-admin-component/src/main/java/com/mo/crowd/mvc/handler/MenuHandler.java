package com.mo.crowd.mvc.handler;

import com.mo.crowd.entity.Menu;
import com.mo.crowd.service.api.MenuService;
import com.mo.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-10  02:04
 * @description:
 */
@Controller
public class MenuHandler {
    @Autowired
    MenuService menuService;

    @ResponseBody
    @RequestMapping("menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTree(){
        // 获取所有的Menu对象
        List<Menu> all = menuService.getAll();

        // 声明一个根结点
        Menu root = null;

        // 创建一个Map对象来存储id-menu对应关系
        HashMap<Integer, Menu> hashMap = new HashMap<>();

        // 循环list填充map
        for (Menu menu : all){

            Integer id = menu.getId();

            hashMap.put(id, menu);
        }


        for(Menu menu : all){
            // 获取当前节点的父节点id
            Integer pid = menu.getPid();

            // 找到根结点
            if(pid == null) {
                root = menu;
                continue;
            }

            // 获取当前的节点的父节点对象
            Menu father = hashMap.get(pid);

            // 父节点添加当前子节点对象
            father.getChildren().add(menu);
        }

        return ResultEntity.successWithData(root);
    }

    // 新增节点
    @ResponseBody
    @RequestMapping("menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();

    }

    // 更新节点
    @ResponseBody
    @RequestMapping("menu/update.json")
    public ResultEntity<String> udateMenu(Menu menu){
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();

    }

    // 删除节点
    @ResponseBody
    @RequestMapping("menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id){
        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();

    }
}