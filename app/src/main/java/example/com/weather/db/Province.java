package example.com.weather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 14064 on 2017/4/23.
 */

public class Province extends DataSupport {
    private int id;
    private String provinceName; //省的名字
    private int provinceCode;//省得代号
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
