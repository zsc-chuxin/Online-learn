import request from "@/utils/request.js";

export const userRegisterService = async (formData) => {
    try {
        const response = await request.post('/api/register', formData);
        console.log('Response:', response); // 输出响应数据

        if (!response) {
            console.error('无效的响应:', response);
            return { code: 1, message: '注册失败' };
        }

        const responseData = response.data;

        if (!responseData) {
            console.error('无效的响应数据:', responseData);
            return { code: 1, message: '注册失败' };
        }

        return responseData;
    } catch (error) {
        console.error('注册失败:', error);
        return { code: 1, message: '注册失败' };
    }
};
