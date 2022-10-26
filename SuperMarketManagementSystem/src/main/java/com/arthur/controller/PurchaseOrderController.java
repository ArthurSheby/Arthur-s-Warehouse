package com.arthur.controller;

import com.arthur.controller.replaceEntity.PurchaseDetailed;
import com.arthur.entity.*;
import com.arthur.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.schema.Entry;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 20:26
 **/
@RestController
@RequestMapping("/purchase")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private PurchaseGoodsService purchaseGoodsService;

    @GetMapping("/getAllPurchas")
    public List<PurchaseDetailed> getAllPurchaseOrder( @RequestParam Integer pageNum,
                                                       @RequestParam Integer pageSize,
                                                       @RequestParam(defaultValue = "dashabi") String inputState,
                                                       @RequestParam(defaultValue = "dashabi") String inputOrderID){
        System.out.println("inputState is "+inputState);
        System.out.println("inputOrderID is "+inputOrderID);
        //添加筛选
        IPage<PurchaseOrder> page = new Page<>(pageNum,pageSize);
        QueryWrapper<PurchaseOrder> queryWrapper = new QueryWrapper<>();
        if (!inputOrderID.equals("dashabi")){
            queryWrapper.like("order_number",inputOrderID);
        }
        if (!inputState.equals("dashabi")){
            queryWrapper.eq("status",inputState);
        }
        queryWrapper.orderByDesc("id");

        List<PurchaseDetailed> purchaseDetailedList = new ArrayList<>();
        //改动
        System.out.println("@@@@@@@@@@@@@getRecords"+purchaseOrderService.page(page,queryWrapper).getRecords());
        for (PurchaseOrder purchase : purchaseOrderService.page(page,queryWrapper).getRecords()){
            PurchaseDetailed detailed = new PurchaseDetailed();
            List<Goods> goodsList = new ArrayList<>();
            List<Integer> goodsNumList = new ArrayList<>();

            List<PurchaseGoods> purchaseGoodsList = purchaseGoodsService.list(
                    new QueryWrapper<PurchaseGoods>().eq("purchase_order_id",purchase.getOrderNumber()));
            for (PurchaseGoods goods :purchaseGoodsList){
                goodsList.add(goodsService.getById(goods.getGoodsId()));
                goodsNumList.add(goods.getGoodsNumber());
            }
            detailed.setGoodsNumList(goodsNumList);
            detailed.setGoodsList(goodsList);

            detailed.setCreator(userService.getById(purchase.getCreatorId()));
            detailed.setChecker(userService.getById(purchase.getCheckerId()));
            detailed.setWarehouseman(userService.getById(purchase.getWarehouseman()));
            detailed.setWarehouse(wareHouseService.getById(purchase.getWarehouseId()));
            detailed.setSupplier(supplierService.getById(purchase.getSupplierId()));
            detailed.setCheckTime(purchase.getCheckTime());
            detailed.setCreateTime(purchase.getCreateTime());
            detailed.setId(purchase.getId());
            detailed.setOrderNumber(purchase.getOrderNumber());
            if (purchase.getStatus() == 0){
                detailed.setStatus("未审核");
                detailed.setBottonClass("info");
            }else if(purchase.getStatus() == 1){
                detailed.setStatus("未入库");
                detailed.setBottonClass("");
            }else if (purchase.getStatus() == 2){
                detailed.setStatus("已完成");
                detailed.setBottonClass("success");
            }

            detailed.setPutTime(purchase.getPutTime());
            purchaseDetailedList.add(detailed);
        }
        System.out.println("修改后的数组@@@@@@@"+purchaseDetailedList.toString());
        return purchaseDetailedList;
    }

    @PostMapping("deleteManyPurchaseOrder")
    public boolean deleteManyPurchaseOrder(@RequestBody List<Integer> list){
        return purchaseOrderService.removeByIds(list);
    }

//    删除进货订单
    @DeleteMapping("/detelePurchaseOrder/{id}")
    public boolean deletePurchaseOrder(@PathVariable Integer id){
        PurchaseOrder purchaseOrder = purchaseOrderService.getById(id);
        purchaseGoodsService.remove(new QueryWrapper<PurchaseGoods>().eq("purchase_order_id",purchaseOrder.getOrderNumber()));
        return purchaseOrderService.removeById(id);
    }

    @PostMapping("/insertPurchaseOrder")
    public boolean insertPurchaseOrder(@RequestBody HashMap<String,Object> dynamicValidateForm){
        PurchaseOrder order = new PurchaseOrder();
        if (dynamicValidateForm.get("domains") instanceof ArrayList<?>) {
            List<?> list = (List<?>) dynamicValidateForm.get("domains");
            PurchaseGoods good = new PurchaseGoods();
            for (Object o : list) {
                good = new PurchaseGoods();
                if (o instanceof LinkedHashMap<?,?>){
                    LinkedHashMap<String,Object> hashMap = (LinkedHashMap<String, Object>)o;
                    good.setGoodsNumber((int)hashMap.get("goodsNumber"));
                    good.setGoodsId((int)hashMap.get("goodsId"));
                    good.setPurchaseOrderId(Long.toString((Long) hashMap.get("purchaseOrderId")));
                    purchaseGoodsService.save(good);
                    System.out.println("good@@@@  "+good.toString());
                }
            }
//            System.out.println("@@@@order"+order.toString());
            order.setOrderNumber(good.getPurchaseOrderId());
            order.setCreatorId((Integer) dynamicValidateForm.get("createrId"));
            order.setStatus(0);
        }
        return purchaseOrderService.save(order);
    }

    @PostMapping("/approveOrder")
    public boolean approveOrder(@RequestBody HashMap<String,Object> dataMap){
        String orderNumber = (String) dataMap.get("orderNumber");
        //添加筛选
        QueryWrapper<PurchaseOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_number",orderNumber);
        //通过orderNumber查询purchaseOrder
        PurchaseOrder purchaseOrder = purchaseOrderService.getOne(queryWrapper);
        purchaseOrder.setCheckerId((Integer) dataMap.get("checkerId"));
        purchaseOrder.setStatus(1);
        //获取当前时间String类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(new Date());
        purchaseOrder.setCheckTime(dateNowStr);
        return purchaseOrderService.saveOrUpdate(purchaseOrder);
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@"+purchaseOrder.toString());
    }
    @PostMapping("/putInWarehousing")
    public boolean putInWarehousing(@RequestBody HashMap<String,Object> dataMap){
        String orderNumber = (String) dataMap.get("orderNumber");
        //添加筛选
        QueryWrapper<PurchaseOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_number",orderNumber);
        PurchaseOrder purchaseOrder = purchaseOrderService.getOne(queryWrapper);
        //添加属性
        purchaseOrder.setStatus(2);
        purchaseOrder.setWarehouseId((Integer) dataMap.get("warehouseId"));
        purchaseOrder.setWarehouseman((Integer) dataMap.get("warehouseman"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(new Date());
        purchaseOrder.setPutTime(dateNowStr);
        return purchaseOrderService.saveOrUpdate(purchaseOrder);
//        System.out.println("@@@@@@purchaseOrder.toString() is "+purchaseOrder.toString());
//        System.out.println("putInWarehousing dataMap is "+dataMap.toString());
//        return true;
    }

}
