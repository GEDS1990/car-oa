package com.xiaofan.car.biz;

import com.github.pagehelper.PageInfo;
import com.xiaofan.car.persistence.param.DeviceInfoParam;
import com.xiaofan.car.persistence.param.DeviceParam;
import com.xiaofan.car.persistence.vo.DeviceInfoVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备管理业务层
 *
 * @author gongdaoshun
 * @date 2017/10/15
 * @since 1.0.0
 */
public interface DeviceBiz {

    /**
     * 查询设备列表
     * @param deviceParam
     * @return
     */
    public PageInfo<DeviceInfoVo> getDeviceList(DeviceParam deviceParam);

    /**
     * 查询设备详情
     * @param deviceId
     * @return
     */
    public DeviceInfoVo getDeviceDetail(Integer deviceId);

    /**
     * 新增设备信息
     * @param deviceInfoParam
     * @return
     */
    public DeviceInfoVo addDevice(DeviceInfoParam deviceInfoParam);

    /**
     * 删除设备信息
     * @param deviceId
     */
    public void deleteDevice(Integer deviceId);

    /**
     * 更新设备信息
     * @param deviceInfoParam
     */
    public void updateDevice(DeviceInfoParam deviceInfoParam);

    /**
     * 批量新增设备
     */
    public boolean addDevice(MultipartFile multipartFile);
}
