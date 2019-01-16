package aliyun;

import java.util.Random;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SendMess {

	public static String send(String phone) throws ClientException {
		// TODO Auto-generated method stub
		// ���ó�ʱʱ��-�����е���
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// ��ʼ��ascClient��Ҫ�ļ�������
		final String product = "Dysmsapi";// ����API��Ʒ���ƣ����Ų�Ʒ���̶��������޸ģ�
		final String domain = "dysmsapi.aliyuncs.com";// ����API��Ʒ�������ӿڵ�ַ�̶��������޸ģ�
		// �滻�����AK
		final String accessKeyId = "LTAIYNyaF6eUWPg1";// ���accessKeyId,�ο����ĵ�����2
		final String accessKeySecret = "lvAPfLOQzGniHJfSll5cS50nO3JMdM";// ���accessKeySecret���ο����ĵ�����2
		// ��ʼ��ascClient,��ʱ��֧�ֶ�region�������޸ģ�
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		Random random = new Random();
		String number = "";
		for (int i = 0; i < 6; i++) {
			number += random.nextInt(10);
		}
		// ��װ�������
		SendSmsRequest request = new SendSmsRequest();
		// ʹ��post�ύ
		request.setMethod(MethodType.POST);
		// ����:�������ֻ��š�֧���Զ��ŷָ�����ʽ�����������ã���������Ϊ1000���ֻ�����,������������ڵ������ü�ʱ�������ӳ�,��֤�����͵Ķ����Ƽ�ʹ�õ������õķ�ʽ�����͹���/�۰�̨��Ϣʱ�����պ����ʽΪ00+��������+���룬�硰0085200000000��
		request.setPhoneNumbers(phone);
		// ����:����ǩ��-���ڶ��ſ���̨���ҵ�
		request.setSignName("���ڿ���");
		// ����:����ģ��-���ڶ��ſ���̨���ҵ�
		request.setTemplateCode("SMS_135801792");
		// ��ѡ:ģ���еı����滻JSON��,��ģ������Ϊ"�װ���${name},������֤��Ϊ${code}"ʱ,�˴���ֵΪ
		// ������ʾ:���JSON����Ҫ�����з�,����ձ�׼��JSONЭ��Ի��з���Ҫ��,������������а���\r\n�������JSON����Ҫ��ʾ��\\r\\n,����ᵼ��JSON�ڷ���˽���ʧ��
		request.setTemplateParam("{\"code\":" + number + "}");
		// ��ѡ-���ж�����չ��(��չ���ֶο�����7λ�����£������������û�����Դ��ֶ�)
		// request.setSmsUpExtendCode("90997");
		// ��ѡ:outIdΪ�ṩ��ҵ����չ�ֶ�,�����ڶ��Ż�ִ��Ϣ�н���ֵ���ظ�������
		request.setOutId("yourOutId");
		// ����ʧ���������ClientException�쳣
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			return number;
		}
		return null;
	}

}
