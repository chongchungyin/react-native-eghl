#import "Eghl.h"
#import <EGHL/EGHL.h>
#import <UIKit/UIKit.h>

@implementation Eghl

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(
    startPayment:(NSDictionary *)paymentParams
    callback:(RCTResponseSenderBlock)callback
  )
{
    EGHLPayment * eghlPayment = [[EGHLPayment alloc]init];
    PaymentRequestPARAM * param = [[PaymentRequestPARAM alloc] init];
    
    if([paymentParams objectForKey:@"TransactionType"] != nil){
        param.TransactionType = [paymentParams objectForKey:@"TransactionType"];
    }
    
    if([paymentParams objectForKey:@"Amount"] != nil){
        param.Amount = [paymentParams objectForKey:@"Amount"];
    }
    
    if([paymentParams objectForKey:@"CurrencyCode"] != nil){
        param.CurrencyCode = [paymentParams objectForKey:@"CurrencyCode"];
    }
    
    if([paymentParams objectForKey:@"PaymentID"] != nil){
        param.PaymentID = [paymentParams objectForKey:@"PaymentID"];
    }
    
    if([paymentParams objectForKey:@"OrderNumber"] != nil){
        param.OrderNumber = [paymentParams objectForKey:@"OrderNumber"];
    }
    
    if([paymentParams objectForKey:@"PaymentDesc"] != nil){
        param.PaymentDesc = [paymentParams objectForKey:@"PaymentDesc"];
    }

    if([paymentParams objectForKey:@"PymtMethod"] != nil){
        param.PymtMethod = [paymentParams objectForKey:@"PymtMethod"];
    }

    if([paymentParams objectForKey:@"CustEmail"] != nil){
        param.CustEmail = [paymentParams objectForKey:@"CustEmail"];
    }

    if([paymentParams objectForKey:@"CustName"] != nil){
        param.CustName = [paymentParams objectForKey:@"CustName"];
    }

    if([paymentParams objectForKey:@"CustPhone"] != nil){
        param.CustPhone = [paymentParams objectForKey:@"CustPhone"];
    }
    
    if([paymentParams objectForKey:@"ServiceID"] != nil){
        param.ServiceID = [paymentParams objectForKey:@"ServiceID"];
    }
    
    if([paymentParams objectForKey:@"Password"] != nil){
        param.Password = [paymentParams objectForKey:@"Password"];
    }
    
    if([paymentParams objectForKey:@"LanguageCode"] != nil){
        param.LanguageCode = [paymentParams objectForKey:@"LanguageCode"];
    }
    
    if([paymentParams objectForKey:@"PageTimeout"] != nil){
        param.PageTimeout = [paymentParams objectForKey:@"PageTimeout"];
    }

    if([paymentParams objectForKey:@"MerchantName"] != nil){
        param.MerchantName = [paymentParams objectForKey:@"MerchantName"];
    }
    
    if([paymentParams objectForKey:@"Prod"] != nil){
        BOOL Prod = [[paymentParams objectForKey:@"Prod"] boolValue];
        if(Prod == false){
            param.settingDict = @{
            EGHL_DEBUG_PAYMENT_URL:[NSNumber numberWithBool:true],
            };
        }
    }
    
    param.MerchantReturnURL = @"SDK";
    
    dispatch_async(dispatch_get_main_queue(), ^{
        [eghlPayment execute:param fromViewController:[self topViewController] successBlock:^(PaymentRespPARAM *responseData) {
            [[self topViewController] dismissViewControllerAnimated:true completion:nil];
            callback(@[@"OK"]);
        } failedBlock:^(NSString *errorCode, NSString *errorData, NSError *error) {
            callback(@[@"ERROR"]);
        }];
    });
    
}

- (UIViewController *)topViewController{
  return [self topViewController:[UIApplication sharedApplication].keyWindow.rootViewController];
}

- (UIViewController *)topViewController:(UIViewController *)rootViewController
{
  if (rootViewController.presentedViewController == nil) {
    return rootViewController;
  }

  if ([rootViewController.presentedViewController isKindOfClass:[UINavigationController class]]) {
    UINavigationController *navigationController = (UINavigationController *)rootViewController.presentedViewController;
    UIViewController *lastViewController = [[navigationController viewControllers] lastObject];
    return [self topViewController:lastViewController];
  }

  UIViewController *presentedViewController = (UIViewController *)rootViewController.presentedViewController;
  return [self topViewController:presentedViewController];
}

@end
