import { Moment } from 'moment';
import { IPurchaseItem } from 'app/shared/model/purchase-item.model';

export interface IPurchase {
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
  items?: IPurchaseItem[];
  contactId?: number;
  warehouseId?: number;
}

export class Purchase implements IPurchase {
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
    public items?: IPurchaseItem[],
    public contactId?: number,
    public warehouseId?: number
  ) {
    this.discPercentStatus = this.discPercentStatus || false;
  }
}
