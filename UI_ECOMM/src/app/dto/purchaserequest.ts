import { Address } from "./address";
import { Coustomer } from "./coustomer";
import { Order } from "./order";
import { OrderItems } from "./order-items";

export class PurchaseRequest {
    public customer!: Coustomer;
    public order!: Order;
    public address!: Address;
    public orderItems!: OrderItems[];
}
