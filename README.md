# react-native-eghl

## Getting started

`$ npm install react-native-eghl --save`

### Mostly automatic installation

`$ react-native link react-native-eghl`

## Usage
```javascript
Eghl.startPayment(
  {
    TransactionType: 'SALE',
    Amount: '1.00',
    CurrencyCode: 'MYR',
    PaymentID: 'paymentId001',
    OrderNumber: 'orderId001',
    PaymentDesc: 'Testing Payment',
    PymtMethod: 'ANY',

    CustEmail: 'somebody@someone.com',
    CustName: 'Somebody',
    CustPhone: '0123456789',

    MerchantName: 'eGHL Payment Testing',
    MerchantReturnURL: 'SDK',

    ServiceID: 'SIT',
    Password: 'sit12345',

    LanguageCode: 'EN',
    PageTimeout: '600',

    Prod: false,
  },
  (message) => {
    this.setState({
      status: 'native callback received',
      message,
    });
  },
);
```
