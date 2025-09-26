import React, { useState, useRef } from "react";
import { Card, Row, Col, Typography, Checkbox, Button, Modal, Form, Input } from "antd";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { CheckCircleOutlined } from "@ant-design/icons";
import napidLogo from "../assets/napidLogo.png";
import napidmfaflow from "../assets/napidmfaflow.svg";
import tick from "../assets/tick.png";

const { Title, Paragraph, Link } = Typography;

const NapidPreAuthMFA = () => {
    const [checked, setChecked] = useState(false);
    const [paymentSuccess, setPaymentSuccess] = useState(false);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [timeCount, setTimeCount] = useState(30);
    const [mobileNumber, setMobileNumber] = useState('8925079283');
    const [verifed, setVerifed] = useState(false);
    const [statusMessage, setStatusMessage] = useState("This is my number");
    const [isIdSleeping, setIsIdSleeping] = useState(false);
    const intervalRef = useRef(null);
    const navigate = useNavigate();

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
                `${import.meta.env.VITE_API_BASE_URL}/napid/v1/application/checkstatus/${mobileNumber}`
            );

            if (response.data.isEnabled) {
                setIsIdSleeping(false)
                await axios.put(`${import.meta.env.VITE_API_BASE_URL}/napid/v1/application`, {
                    id: mobileNumber,
                    isEnabled: false,
                });

                setVerifed(true);
                setStatusMessage("Number is Awake");
                let counter = 30;
                intervalRef.current = setInterval(() => {
                    counter -= 1;
                    setTimeCount(counter);
                    if (counter <= 0) {
                        clearInterval(intervalRef.current);
                        setChecked(false);
                        setVerifed(false);
                        setIsIdSleeping(false)
                        setStatusMessage("This is my number");
                    }
                }, 1000);
            } else {
                setIsIdSleeping(true)
                setStatusMessage("Number is Sleep, Awake");
                setVerifed(false);
            }
        } catch (error) {
            console.error("API Error:", error);
            setStatusMessage("Error checking status");
        }
    };

    return (
        <>
            <Row justify="end" >
                <Col>
                    <Button
                        type="primary"
                        onClick={() => navigate('/dashboard')}
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
                    minHeight: "100vh",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    background: "#f5f5f5",
                    // padding: "16px",
                }}
            >
                <Card
                    style={{
                        width: "80%",
                        height: "100%",
                        // maxWidth: 700,
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
                            lg={12}
                            xl={12}
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
                                level={1}
                                style={{
                                    marginBottom: 16,
                                    marginLeft: 28,
                                    fontWeight: "bold",
                                }}
                            >
                                NapID PRE-AUTH MFA (OTP Replacer)
                            </Title>
                            <Paragraph
                                style={{
                                    fontSize: 18,
                                    marginBottom: 24,
                                    marginLeft: 28,
                                    color: "#162b75",
                                    fontWeight: "bold",
                                }}
                            >
                                For Net Banking, Cards, NEFT, RTGS, IMPS Payments
                            </Paragraph>
                            <Title
                                level={4}
                                style={{
                                    color: "#fd7e14",
                                    marginLeft: 28,
                                    marginBottom: 8,
                                    fontWeight: "bold",
                                }}
                            >
                                One-Touch Authenticator
                            </Title>
                            <Title
                                level={4}
                                style={{
                                    color: "#162b75",
                                    marginLeft: 28,
                                    cursor: "pointer",
                                    fontWeight: "bold",
                                    textDecoration: "underline",
                                }}
                                onClick={() => setIsModalVisible(true)}
                            >
                                How it works ?
                            </Title>
                        </Col>
                        <Col
                            xs={24}
                            sm={24}
                            md={12}
                            lg={12}
                            xl={12}
                            style={{
                                background: "linear-gradient(120deg,rgb(253, 161, 41) 0%, #f26522 100%)",
                                clipPath: "ellipse(95% 95% at 100% 35%)",
                                padding: "40px 16px",
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                            }}
                        >
                            {!paymentSuccess && (
                                <Card
                                    style={{
                                        width: "65%",
                                        height: "92%",
                                        textAlign: "center",
                                        margin: "0px 45px 0px auto",
                                        borderRadius: 12,
                                    }}
                                >
                                    <h2 style={{ color: "#162b75", marginTop: "-5px" }}>
                                        AUTHENTICATE
                                    </h2>
                                    <h2 style={{ color: "#162b75", marginTop: "-23px" }}>
                                        YOUR ACCOUNT
                                    </h2>
                                    <div style={{ margin: "20px 0" }}>
                                        <img src={napidLogo} alt="shield" width={55} />
                                        <Paragraph
                                            strong
                                            style={{
                                                fontSize: 18,
                                                marginTop: 10,
                                                color: "#162b75",
                                                marginBottom: 0,
                                            }}
                                        >
                                            +91 xxxxxx9283

                                        </Paragraph>
                                        {!verifed && !isIdSleeping ? <Checkbox className="custom-checkbox"
                                            style={{ marginTop: '-18px' }}
                                            checked={checked}
                                            onChange={handleCheckboxChange}
                                        >
                                            <h5 size="small" style={{ color: 'red' }}>{statusMessage}</h5>
                                        </Checkbox> : !isIdSleeping ?
                                            <h5 style={{ color: 'green', marginTop: '0px', marginLeft: '-25px' }}>
                                                {statusMessage} <span style={{ marginLeft: '8px', color: 'red' }}>{timeCount}</span>
                                            </h5> :
                                            <><h5 style={{ color: 'red', marginTop: '0px', marginLeft: '-25px' }}>
                                                {statusMessage}
                                            </h5><h5 onClick={handleVerify
                                            } style={{ color: 'blue', marginTop: '-20px', marginLeft: '-25px', cursor: 'pointer' }}>
                                                    Awake & Recheck
                                                </h5></>
                                        }
                                    </div>
                                    <Button
                                        type="primary"
                                        onClick={() => {
                                            setPaymentSuccess(true);
                                            if (intervalRef.current) {
                                                clearInterval(intervalRef.current);
                                                intervalRef.current = null;
                                            }
                                            setChecked(false);
                                            setVerifed(false);
                                            setIsIdSleeping(false)
                                            setStatusMessage("This is my number");
                                            setTimeCount(30);
                                        }}
                                        shape="round"
                                        style={{ width: "45%", fontSize: 18,fontWeight:'bold', marginTop: '-30px', backgroundColor: (!verifed) ? "#bfbfbf" : "#162b75" , color:(!verifed)?'#595959':"white"}}
                                        size="middle"
                                        disabled={!verifed}
                                    >
                                        Verify
                                    </Button>
                                </Card>
                            )}
                            {paymentSuccess && (
                                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%', height: '100%' }}>
                                    <Card
                                        style={{
                                            width: "65%",
                                            height: "92%",
                                            textAlign: "center",
                                            margin: "0px 45px 0px auto",
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
                        </Col>
                    </Row>
                </Card>
                <Modal
                    title="How it works"
                    open={isModalVisible}
                    onCancel={() => setIsModalVisible(false)}
                    footer={null}
                    width={'60%'}
                    centered
                >
                    <div>
                        <img
                            src={napidmfaflow}
                            alt="NapID MFA Flow"
                            style={{ maxWidth: "100%", height: "auto" }} />
                    </div>
                </Modal>
            </div></>
    );
};

export default NapidPreAuthMFA;
