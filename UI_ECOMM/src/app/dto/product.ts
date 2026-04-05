export class Product {
    constructor(
    public id?:number,
	public name?:string,
    public description?:String,
    public title?:String,
    public unitPrice?:number,
    public imageUrl?:string,
    public active?:boolean,
	public unitsInStock?:number,
	public dateCreated?:Date,
	public lastUpdate?:Date
    ){}
}
