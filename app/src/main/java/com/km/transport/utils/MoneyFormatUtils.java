package com.km.transport.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by kamangkeji on 17/2/13.
 */

public class MoneyFormatUtils {

    /**
     * 根据格式类型（pattern）格式化数据
     * @param pattern
     * @param bd
     * @return
     */
    private static String parseMoney(String pattern,BigDecimal bd){
        DecimalFormat df=new DecimalFormat(pattern);
        return df.format(bd);
    }

    /**
     * 将money值转换为千分位格式的
     * @param money
     * @return
     */
    public static String formatMonry(String money){
        if (TextUtils.isEmpty(money)){
            return "";
        }
        BigDecimal bd = new BigDecimal(money);
        return parseMoney(",###,##0.00",bd);
    }
    /**
     * 将money值转换为千分位格式的
     * @param money
     * @return
     */
    public static String formatMonry(int money){
        BigDecimal bd = new BigDecimal(money);
        return parseMoney(",###,##0.00",bd);
    }

    public static void setEdittext(final EditText edittext){
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String content = edittext.getText().toString();
                    edittext.setText(MoneyFormatUtils.formatMonry(content));
                }
            }
        });
    }

    public static String formatDecimalsToPercent(String decimals){
        if (TextUtils.isEmpty(decimals)){
            return "";
        }
        if (decimals.contains("%")){
            decimals = decimals.substring(0,decimals.length() - 1);
        }
        double decimal = Double.parseDouble(decimals);
        DecimalFormat df = new DecimalFormat("0.00%");
        return df.format(decimal);

    }
}
