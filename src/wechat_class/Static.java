package wechat_class;

public class Static {
    private int num1;  //����
    private int num2;  //�ٵ�
    private int num3;  //����
    private int num4;  //ȱ��
    private boolean flag;  //�����Ƿ���Ч
    private int timeflag;
	
    public Static(int timeflag) {
		super();
		this.num1 = 0;
		this.num2 = 0;
		this.num3 = 0;
		this.num4 = 0;
		this.flag = false;
		this.timeflag = timeflag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getNum1() {
		return num1;
	}
	public void setNum1() {
		this.num1 ++;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2() {
		this.num2 ++;
	}
	public int getNum3() {
		return num3;
	}
	public void setNum3() {
		this.num3 ++;
	}
	public int getNum4() {
		return num4;
	}
	public void setNum4() {
		this.num4 ++;
	}
	public int getTimeflag() {
		return timeflag;
	}
      
}
