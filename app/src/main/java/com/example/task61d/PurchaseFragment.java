package com.example.task61d;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

public class PurchaseFragment extends Fragment {
    private CardInputWidget cardNumberEdit; // 声明卡片输入控件
    private Stripe stripe; // 声明Stripe对象
    private AppCompatButton payButton; // 声明支付按钮

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 使用发布密钥初始化Stripe
        PaymentConfiguration.init(requireContext(), "pk_test_YourPublishableKey");
        // 创建Stripe实例
        stripe = new Stripe(requireContext(), PaymentConfiguration.getInstance(requireContext()).getPublishableKey());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 将布局文件膨胀为View对象
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);
        // 初始化控件并设置点击监听器
        initViews(view);
        return view; // 返回视图
    }

    // 初始化控件并设置监听器
    private void initViews(View view) {
        cardNumberEdit = view.findViewById(R.id.cardNumberEdit);
        payButton = view.findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePurchase(); // 调用处理支付逻辑的方法
            }
        });
    }

    // 处理支付逻辑的方法
    private void handlePurchase() {
        Card card = cardNumberEdit.getCard();
        if (card != null) {
            stripe.createToken(card, new ApiResultCallback<Token>() {
                @Override
                public void onSuccess(@NonNull Token token) {
                    displayToast("Token: " + token.getId());
                }

                @Override
                public void onError(@NonNull Exception e) {
                    displayToast("Error: " + e.getLocalizedMessage());
                }
            });
        } else {
            displayToast("Invalid card data");
        }
    }

    // 显示Toast消息的方法
    private void displayToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
