import { Moment } from 'moment';
import { ISalesItem } from 'app/shared/model/sales-item.model';

export interface ISales {
  id?: number;
  typeId?: number;
  date?: Moment;
  refNo?: string;
  description?: string;
  otherCost?: number;
  subtotal?: number;
  totalTax?: number;
  disc?: number;
  discPercentStatus?: boolean;
  status?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  recordStatusId?: number;
  items?: ISalesItem[];
  contactId?: number;
  warehouseId?: number;
}

export class Sales implements ISales {
  constructor(
    public id?: number,
    public typeId?: number,
    public date?: Moment,
    public refNo?: string,
    public description?: string,
    public otherCost?: number,
    public subtotal?: number,
    public totalTax?: number,
    public disc?: number,
    public discPercentStatus?: boolean,
    public status?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public recordStatusId?: number,
    public items?: ISalesItem[],
    public contactId?: number,
    public warehouseId?: number
  ) {
    this.discPercentStatus = this.discPercentStatus || false;
  }
}
