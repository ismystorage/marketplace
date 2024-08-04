# API

## Описание сущности order (заказ)
1. Order
   1. id: Long (первичный ключ)
   2. createdAt: Instant (дата создания)
   3. updatedAt: Instant (дата изменения)
   4. orderNumber: String (номер заказа)
   5. status: OrderStatus (статус заказа)
   5. bestPackage: Item? (лучшая упаковка, внешний ключ)
   6. quantity: Int (количество лучшей упаковки)
## Функции (эндпониты)
1. CRUDS (create, read, update, delete, search) для заказа (order)
2. calculate - вычислить лучшую упаковку

## Описание сущности item (элемент заказа)
2. Item
   1. id: Long (первичный ключ)
   2. length: Int (длина в миллиметрах)
   3. width: Int (ширина в миллиметрах)
   4. height: Int (высота в миллиметрах)
   5. weight: Int (вес в граммах)
   6. type: ItemType (тип элемента)
   7. order: Order? (заказ, внешний ключ)
## Функции (эндпониты)
1. create, read, delete для элементов заказа (item)