# API

## Описание сущности order (заказ)

1. Order
    1. id: Long (первичный ключ)
    2. visibility: OrderVisibility (тип видимости заказа)
    3. createdAt: Instant (дата создания)
    4. updatedAt: Instant (дата изменения)
    5. orderNumber: String (номер заказа)
    6. status: OrderStatus (статус заказа)
    7. packages: List<PackageType> (выбранный тип упаковки)

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

1. CRUDS (create, read, update, delete, search) для элементов заказа (item)
2. availablePackage - возвращает список доступных упаковок для элемента.
