package vendingmachine.goods;

import vendingmachine.exception.VendingMachineException;

import java.util.HashMap;

public class GoodsManager {

    private final HashMap<String, Goods> goodsInfo = new HashMap<>();
    private int minPrice = Integer.MAX_VALUE;
    VendingMachineException vendingMachineException = new VendingMachineException();

    public GoodsManager(String goodsList){
        makeGoodsInformation(goodsList);
        minPriceGoodsList();
    }

    private void makeGoodsInformation(String goodsList) {
        String[] itemList = goodsList.split(";");
        for (String item : itemList) {
            String[] goodsInformation = item.replace("[", "").replace("]", "").split(",");
            vendingMachineException.validGoodsMoneyType(goodsInformation[1]);

            Goods goods = new Goods(goodsInformation);
            goodsInfo.put(goodsInformation[0],goods);
        }
    }

    private void minPriceGoodsList() {
        for (Goods g : goodsInfo.values())
            if (g.getPrice() < minPrice) minPrice = g.getPrice();
    }

    public boolean validPurchase(String purchaseGoods, int money) {
        vendingMachineException.validGoodsName(purchaseGoods,goodsInfo);
        Goods goods = goodsInfo.get(purchaseGoods);
        return goods.getPrice() <= money && goods.getAmount() != 0;
    }

    public boolean validMinPurchase(int money) {
        return money >= minPrice;
    }

    public int purchaseGoods(String purchaseGoods, int money) {
        Goods goods = goodsInfo.get(purchaseGoods);
        if (goods.getAmount() != 0)
            goods.reduceAmount();
        return money - goods.getPrice();
    }


}
