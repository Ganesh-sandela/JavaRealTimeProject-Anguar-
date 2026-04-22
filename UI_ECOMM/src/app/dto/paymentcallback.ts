export class Paymentcallback {
    constructor(
        private razorPayOrderId:string,
        private razorPayPaymentId:string,
        private razorpaySignature:string
    ){}
}
