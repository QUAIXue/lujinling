package service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
//获得余额
    public BigDecimal getWalletBalance(int userId) {
        UserWallet userWallet = walletRepository.findByUserId(userId);
        if (userWallet != null) {
            return userWallet.getBalance();
        }
        return BigDecimal.ZERO;
    }

//    查询用户钱包金额变动明细
    public List<WalletTransaction> getWalletTransactions(int userId) {
        UserWallet userWallet = walletRepository.findByUserId(userId);
        if (userWallet != null) {
            return userWallet.getTransactions();
        }
        return Collections.emptyList();
    }
}
