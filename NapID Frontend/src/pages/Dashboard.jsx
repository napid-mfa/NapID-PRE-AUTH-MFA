import React from "react";
import { Card, Row, Col, Button, Typography, Tag } from "antd";
import { useNavigate } from "react-router-dom";
import {
    SafetyCertificateOutlined
} from "@ant-design/icons";

const { Title, Text } = Typography;

function Dashboard() {
    const navigate = useNavigate();

    const applications = [
        {
            id: 1,
            title: "Napid PreAuth MFA",
            // description: "Multi-factor authentication for enhanced security",
            icon: <SafetyCertificateOutlined style={{ fontSize: 24 }} />,
            route: "/napid-preauth-mfa",
            gradient: "linear-gradient(135deg,rgb(253, 161, 41),  #f26522)",
        },
        {
            id: 2,
            title: "Antifraud Payment/Login",
            // description: "Anti-fraud payment system for secure transactions",
            icon: <SafetyCertificateOutlined style={{ fontSize: 24 }} />,
            route: "/antifraud-payment",
            gradient: "linear-gradient(135deg,rgb(253, 161, 41), #f26522)",
        },

    ];


    const AppCard = ({ app }) => (
        <Card
            onClick={() => { navigate(app.route) }}
            hoverable
            style={{
                borderRadius: 16,
                overflow: "hidden",
                height: "100%",
                border: "none",
                boxShadow: "0 4px 20px rgba(0,0,0,0.1)",
            }}
            bodyStyle={{ padding: 0 }}
        >
            <div
                style={{
                    background: app.gradient,
                    padding: 20,
                    textAlign: "center",
                    color: "white",
                }}
            >
                {app.icon}
                <Title level={4} style={{ color: "white", margin: "8px 0 0 0" }}>
                    {app.title}
                </Title>
            </div>
            <div style={{ padding: 20 }}>
                {/* <Text style={{ display: "block", marginBottom: 16 }}>
                    {app.description}
                </Text> */}
                <Button
                    type="primary"
                    block
                    style={{ borderRadius: 8, backgroundColor: '#162b75', color: 'white' }}
                >
                    Open Application
                </Button>
            </div>
        </Card>
    );

    return (
        <Row justify="center" gutter={[24, 24]} style={{ marginTop: '13%' }}>
            <Col xs={24} sm={20} md={16} lg={14} >
                <Card style={{ backgroundColor: "#fafafa", width: "100%" }}>
                    <Row gutter={[16, 16]} justify="center">
                        {applications.slice(0, 2).map((app) => (
                            <Col xs={24} sm={12} key={app.id}>
                                <AppCard app={app} />
                            </Col>
                        ))}
                    </Row>
                </Card>
            </Col>
        </Row>
    );
}

export default Dashboard;
