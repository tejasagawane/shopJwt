drop table `shopdb`.`product` ;
insert into `shopdb`.`product`
select DEMO_ID,BRAND,CATEGORY,COLOR,COSTOFPRICE,DATE_FORMAT(str_to_date(ENTRYDATE, "%d-%m-%Y"), '%Y-%m-%d') ,
CASE WHEN GST = '' THEN 0 ELSE
GST END,
CUSTOMER_PRICE,ITEMNUMBER, QUANTITY,null,
CASE WHEN SELLINGPRICE = 'NA' THEN 0 ELSE
SELLINGPRICE END,
ITEMSIZE, BRAND ,STATUS
from `shopdb`.`demo`;