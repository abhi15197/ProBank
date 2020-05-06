export class Transaction2{

    transType:string;
    transOption:string;
    transAmount:number;
    transFrom:string;
    transTo:string;

    constructor(
        transType:string,
        transOption:string,
        transAmount:number,
        transFrom:string,
        transTo:string){
            this.transAmount=transAmount;
            this.transFrom=transFrom;
            this.transTo=transTo;
            this.transType=transType;
            this.transOption=transOption;
        }      
}