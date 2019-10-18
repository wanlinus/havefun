package cn.wanli.zk;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.nio.charset.StandardCharsets;

/**
 * @author wanli
 * @date 2019-08-28 16:54
 */
public class ZkSerializerImpl implements ZkSerializer {
    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError {
       return String.valueOf(data).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
       return new String(bytes, StandardCharsets.UTF_8);
    }
}
