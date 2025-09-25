import React, { useState, useRef } from "react";
import { Card, Row, Col, Typography, Checkbox, Button, Modal, Form, Input } from "antd";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import napidLogo from "../assets/napidLogo.png";
import napidmfaflow from "../assets/napidmfaflow.svg";
import tick from "../assets/tick.png";

const { Title, Paragraph, Link } = Typography;

const NapidPreAuthMFA = () => {
    const [checked, setChecked] = useState(false);
    const [paymentSuccess, setPaymentSuccess] = useState(false);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [verifed, setVerifed] = useState(false);
    const [userId, setUserId] = useState(null);
    const [statusMessage, setStatusMessage] = useState("This is my id");
    const [statusAwakeMessage, setStatusAwakeMessage] = useState(null);
    const [isIdSleeping, setIsIdSleeping] = useState(false);
    const intervalRef = useRef(null);
    const navigate = useNavigate();
    const [timeCount, setTimeCount] = useState(30);

    const onFinish = (values) => {
        setPaymentSuccess(true)
        clearInterval(intervalRef.current);
        setChecked(false);
        setVerifed(false);
        setIsIdSleeping(false)
        setStatusMessage("This is my id");
        setStatusAwakeMessage(null);
        setTimeCount(30);
    };

    const onFinishFailed = (errorInfo) => {
        console.log("Failed:", errorInfo);
    };

    const handleCheckboxChange = async (e) => {
        setChecked(e.target.checked);

        if (!e.target.checked) {
            setVerifed(false);
            setStatusMessage("");
            return;
        }
        handleVerify()
    };


    const handleVerify = async () => {
        try {
            const response = await axios.get(
                `http://192.168.0.109:8080/napid/v1/application/checkstatus/${userId}`
            );

            if (response.data.isEnabled) {
                setIsIdSleeping(false)
                await axios.put("http://192.168.0.109:8080/napid/v1/application", {
                    id: userId,
                    isEnabled: false,
                });

                setVerifed(true);
                setStatusMessage("Id is Awake");
                let counter = 30;
                intervalRef.current = setInterval(() => {
                    counter -= 1;
                    setTimeCount(counter);
                    if (counter <= 0) {
                        clearInterval(intervalRef.current);
                        setChecked(false);
                        setVerifed(false);
                        setIsIdSleeping(false)
                        setStatusMessage("This is my id");
                    }
                }, 1000);
            } else {
                setIsIdSleeping(true)
                setStatusMessage("Id is Sleep");
                setVerifed(false);
                setStatusAwakeMessage('Awake & Recheck')
            }
        } catch (error) {
            console.error("API Error:", error);
            setStatusMessage(error.response.data.message);
            setChecked(false);
            setVerifed(false);
            setIsIdSleeping(false)
            setIsIdSleeping(true)

        }
    };

    return (
        <><Row justify="end">
            <Col>
                <Button
                    onClick={() => navigate('/dashboard')}
                    type="primary"
                    style={{
                        position: "absolute",
                        top: 16,
                        right: 16,
                        zIndex: 1000,
                        backgroundColor: "#162b75"
                    }}
                >
                    Go To Home
                </Button>
            </Col>
        </Row>
            <div
                style={{
                    minHeight: "90vh",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    background: "#f5f5f5",
                    padding: "16px",
                }}
            >
                <Card
                    style={{
                        width: "100%",
                        maxWidth: 700,
                        borderRadius: 16,
                        overflow: "hidden",
                    }}
                    bodyStyle={{ padding: 0 }}
                >
                    <Row style={{ minHeight: 400 }}>
                        <Col
                            xs={24}
                            sm={24}
                            md={12}
                            lg={9}
                            xl={9}
                            style={{
                                background: "#fff",
                                padding: "32px 20px",
                                display: "flex",
                                flexDirection: "column",
                                justifyContent: "center",
                                alignItems: "flex-start",
                                textAlign: "left",
                            }}
                        >
                            <Title
                                level={3}
                                style={{
                                    marginBottom: 16,
                                    fontWeight: "bold",
                                }}
                            >
                                NapID PRE-AUTH <br /> MFA Screen <br /> (OTP Replacer)
                            </Title>
                            <Paragraph
                                style={{
                                    fontSize: 16,
                                    marginBottom: 24,
                                    color: "#162b75",
                                    fontWeight: "bold",
                                }}
                            >
                                For Net Banking, Cards, NEFT, RTGS, IMPS Payments
                            </Paragraph>
                            <Title
                                level={5}
                                style={{
                                    color: "#fd7e14",
                                    marginBottom: 8,
                                    fontWeight: "bold",
                                }}
                            >
                                One-Touch Authenticator
                            </Title>
                            <Link
                                style={{
                                    color: "#162b75",
                                    cursor: "pointer",
                                    fontWeight: "bold",
                                }}
                                onClick={() => setIsModalVisible(true)}
                            >
                                How it works ?
                            </Link>
                        </Col>
                        <Col
                            xs={24}
                            sm={24}
                            md={12}
                            lg={15}
                            xl={15}
                            style={{
                                background: "linear-gradient(120deg,rgb(253, 161, 41) 0%, #f26522 100%)",
                                clipPath: "ellipse(95% 95% at 100% 35%)",
                                padding: "40px 16px",
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                            }}
                        >
                            {paymentSuccess && (
                                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%', height: '100%' }}>
                                    <Card
                                        style={{
                                            width: "100%",
                                            maxWidth: 270,
                                            textAlign: "center",
                                            margin: "0 auto",
                                            marginRight: "10px",
                                            borderRadius: 12,
                                        }}>
                                        <h2 style={{ color: "#162b75" }}>PAYMENT</h2>
                                        <h2 style={{ color: "#162b75", marginTop: "-28px" }}>
                                            SUCCESSFUL
                                        </h2>
                                        <div style={{ textAlign: "center", padding: "20px" }}>
                                            <img
                                                src={tick}
                                                alt="NapID MFA Flow"
                                                style={{ maxWidth: "80%", height: "auto" }} />
                                        </div>
                                    </Card>
                                    <Button
                                        onClick={() => setPaymentSuccess(false)}
                                        style={{
                                            marginTop: '20px',
                                            width: "30%",
                                            minWidth: 30,
                                            color: 'white',
                                            backgroundColor: '#162b75',
                                            marginRight: '-65%'
                                        }}
                                    >
                                        Test Again
                                    </Button>
                                </div>
                            )}
                            {
                                !paymentSuccess &&
                                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%', height: '100%' }}>
                                    <Card
                                        style={{
                                            width: "100%",
                                            maxWidth: 270,
                                            textAlign: "center",
                                            margin: "0 auto",
                                            marginRight: "10px",
                                            borderRadius: 12,
                                        }}>
                                        <h3 style={{ color: "#162b75", marginTop: "-5px" }}>
                                            AUTHENTICATE
                                        </h3>
                                        <h3 style={{ color: "#162b75", marginTop: "-23px" }}>
                                            YOUR ACCOUNT
                                        </h3>
                                        <div style={{ margin: "20px 0" }}>
                                            <img src={napidLogo} alt="shield" width={55} />
                                            <Form
                                                name="login"
                                                layout="vertical"
                                                onFinish={onFinish}
                                                onFinishFailed={onFinishFailed}
                                                style={{ maxWidth: 400, marginTop: 10 }}
                                            >
                                                <Form.Item
                                                    label="User Id"
                                                    name="username"
                                                    style={{ marginBottom: "8px" }}
                                                >
                                                    <Input
                                                        onChange={(e) => setUserId(e.target.value)}
                                                        disabled={verifed}
                                                        style={{
                                                            backgroundColor: (verifed) ? "#bfbfbf" : "white",
                                                        }}
                                                        placeholder="Enter your User Id"
                                                    />
                                                </Form.Item>
                                                <div style={{ textAlign: 'left', marginBottom: '0px', minHeight: '40px' }}>
                                                    {!verifed && !isIdSleeping ?
                                                        <Checkbox
                                                            checked={checked}
                                                            onChange={handleCheckboxChange}
                                                        >
                                                            <h5 size="small" style={{ color: 'red', margin: 0 }}>{statusMessage}</h5>
                                                        </Checkbox> : !isIdSleeping ?
                                                            <h5 style={{ color: 'green', margin: 0 }}>
                                                                {statusMessage} <span style={{ marginLeft: '8px', color: 'red' }}>{timeCount}</span>
                                                            </h5> :
                                                            <div>
                                                                <h5 style={{ color: 'red', margin: '0 0 8px 0' }}>
                                                                    {statusMessage}
                                                                </h5>
                                                                {statusAwakeMessage && <h5 onClick={handleVerify} style={{ color: 'blue', margin: 0, cursor: 'pointer' }}>
                                                                    {statusAwakeMessage}
                                                                </h5>}
                                                            </div>
                                                    }
                                                </div>
                                                <Form.Item
                                                    label="Password"
                                                    name="password"

                                                >
                                                    <Input
                                                        disabled={!verifed}
                                                        style={{
                                                            backgroundColor: (!verifed) ? "#bfbfbf" : "white",
                                                        }}
                                                        placeholder="Enter your password"
                                                    />
                                                </Form.Item>
                                                <Form.Item>
                                                    <Button
                                                        disabled={!verifed}
                                                        style={{
                                                            width: "30%",
                                                            color: "white",
                                                            backgroundColor: (!verifed) ? "#bfbfbf" : "#162b75",
                                                            marginTop: "0px",
                                                        }}
                                                        htmlType="submit"
                                                        block
                                                    >
                                                        Pay
                                                    </Button>
                                                </Form.Item>
                                            </Form>

                                        </div>
                                    </Card>
                                </div>
                            }
                        </Col>

                    </Row>
                </Card>
                <Modal
                    title="How it works"
                    open={isModalVisible}
                    onCancel={() => setIsModalVisible(false)}
                    footer={null}
                    width={550}
                    centered
                >
                    <div>
                        <img
                            src={napidmfaflow}
                            alt="NapID MFA Flow"
                            style={{ maxWidth: "100%", height: "auto" }} />
                    </div>
                </Modal>
            </div>
        </>
    );
};

export default NapidPreAuthMFA;
