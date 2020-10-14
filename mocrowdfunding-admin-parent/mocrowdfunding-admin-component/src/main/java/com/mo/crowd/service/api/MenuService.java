package com.mo.crowd.service.api;

import com.mo.crowd.entity.Menu;
import java.util.List;

/**
 * @author : xiemogaminari
 * create at:  2020-10-10  02:05
 * @description:
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}