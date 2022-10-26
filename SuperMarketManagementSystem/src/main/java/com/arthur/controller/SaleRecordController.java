package com.arthur.controller;

import com.arthur.entity.SaleRecord;
import com.arthur.service.SaleRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 16:52
 **/
@RestController
@RequestMapping("/sale")
public class SaleRecordController {
    @Autowired
    private SaleRecordService saleRecordService;

    @PostMapping("/insertSale")
    public boolean save(@RequestBody SaleRecord saleRecord){
        return saleRecordService.saveSaleRecord(saleRecord);
    }

    @GetMapping("/getAllSaleRecord")
    public List<SaleRecord> getAllSaleRecord(){
        return saleRecordService.list();
    }

    @GetMapping("/getPartRecord")
    public IPage<SaleRecord> getPartRecord(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String timeValue,
            @RequestParam(defaultValue = "") String salePerson) throws ParseException {
        IPage<SaleRecord> saleRecordIPage = new Page<>(pageNum,pageSize);
        QueryWrapper<SaleRecord> queryWrapper = new QueryWrapper<>();
        if (!"null".equals(salePerson)){
            queryWrapper.like("seller",salePerson);
        }
        if (!"null".equals(timeValue)){
            queryWrapper.like("create_time",timeValue);
            System.out.println("@@@@@@@@  timeValue is "+timeValue);
//            System.out.println("@@@@@@@@   "+date);
        }
        queryWrapper.orderByDesc("id");
        return saleRecordService.page(saleRecordIPage,queryWrapper);
    }

    @PostMapping("/deteleManySaleRecord")
    public boolean deleteManySaleRecord(@RequestBody List<Integer> list){
        return saleRecordService.removeByIds(list);
    }

    @DeleteMapping("/deleteRecordById/{id}")
    public boolean deleteRecord(@PathVariable Integer id){
        return saleRecordService.removeById(id);
    }
}
