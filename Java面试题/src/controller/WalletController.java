package controller;

import service.WalletService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;
//    获得余额
    @GetMapping("/balance")
    public BigDecimal getWalletBalance(@RequestParam("user_id") int userId) {
        return walletService.getWalletBalance(userId);
    }
//  用户消费100的接口
    @PostMapping("/consume")
    public String consumeFromWallet(@RequestBody ConsumeRequest request) {
        int userId = request.getUserId();
//        100
        BigDecimal amount =BigDecimal.valueOf(100);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "Invalid amount";
        }

        BigDecimal balance = walletService.getWalletBalance(userId);
        if (balance.compareTo(amount) >= 0) {
            // 扣除钱包余额
            walletService.decreaseBalance(userId, amount);
            // 创建消费交易记录
            walletService.createTransaction(userId, amount, TransactionType.CONSUME);
            return "Consumed successfully";
        } else {
            return "Insufficient balance";
        }
    }
//用户退款20的接口：
    @PostMapping("/refund")
    public String refundToWallet(@RequestBody RefundRequest request) {
        int userId = request.getUserId();
//        20
        BigDecimal amount = BigDecimal.valueOf(20);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "Invalid amount";
        }

        // 增加钱包余额
        walletService.increaseBalance(userId, amount);
        // 创建退款交易记录
        walletService.createTransaction(userId, amount, TransactionType.REFUND);
        return "Refunded successfully";
    }

//    查询用户钱包金额变动明细的接口：
    @GetMapping("/transactions")
    public List<WalletTransaction> getWalletTransactions(@RequestParam("user_id") int userId) {
        return walletService.getWalletTransactions(userId);
    }

}
