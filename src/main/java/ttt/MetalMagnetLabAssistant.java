package ttt;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 金属滑板 + 磁铁下滑实验 AI 辅助程序 MVP
 *
 * 你现在只需要把传感器平台测到的“距离 distanceMeters”传进 addSensorDistance(...)。
 * 程序会完成：
 * 1. 教师/学生身份切换式实验助手对话
 * 2. 根据距离-时间数据计算速度、加速度、能量
 * 3. 根据电磁制动强弱，估计金属导电率并输出金属概率
 * 4. 检测疑似缝隙、轨迹异常，并进行简单纠正
 * 5. 绘制加速度-时间图像、能量饼图
 * 6. 一键生成结题报告
 *
 * 编译运行：
 * javac MetalMagnetLabAssistant.java
 * java MetalMagnetLabAssistant
 */
public class MetalMagnetLabAssistant extends JFrame {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
            // Nimbus 不可用时使用系统默认外观。
        }
        SwingUtilities.invokeLater(() -> new MetalMagnetLabAssistant().setVisible(true));
    }

    private final ExperimentModel model = new ExperimentModel();
    private final KnowledgeBase knowledgeBase = new KnowledgeBase();
    private final AdvisorEngine advisorEngine = new HybridAdvisorEngine(new OpenAiCompatibleAdvisorEngine(), new RuleBasedAdvisorEngine());

    private final JTextArea chatArea = new JTextArea();
    private final JTextField chatInput = new JTextField();
    private final JComboBox<String> roleCombo = new JComboBox<>(new String[]{"学生", "教师"});
    private final JButton sendBtn = new JButton("发送");
    private final JLabel apiStatusLabel = new JLabel();

    private final JTextField distanceField = new JTextField("0.02");
    private final JTextField dtField = new JTextField("0.10");
    private final JTextField massField = new JTextField("0.050");
    private final JTextField angleField = new JTextField("30");
    private final JTextField copperBrakeField = new JTextField("0.80");

    private final JTextArea resultArea = new JTextArea();
    private final ExperimentChartPanel chartPanel = new ExperimentChartPanel(model, knowledgeBase);

    private final DecimalFormat df3 = new DecimalFormat("0.000");
    private final DecimalFormat df2 = new DecimalFormat("0.00");

    public MetalMagnetLabAssistant() {
        super("金属滑板磁铁下滑实验 AI 助手");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 740));
        setSize(1320, 820);
        setLocationRelativeTo(null);
        initUi();
        appendAssistant("你好，我是这个实验的 AI 辅助助手。你可以先告诉我：你的实验目的是什么？你想验证电磁阻尼、金属电导率，还是想判断未知金属材料？\n\n" + apiHint());
    }

    private void initUi() {
        Color bg = new Color(241, 245, 249);
        Color ink = new Color(15, 23, 42);
        Color muted = new Color(100, 116, 139);
        Color primary = new Color(14, 116, 144);

        getContentPane().setBackground(bg);
        setLayout(new BorderLayout(18, 18));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel title = new JLabel("金属滑板磁铁下滑实验 AI 助手");
        title.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 26));
        title.setForeground(ink);

        JLabel subtitle = new JLabel("采集距离数据，实时分析阻尼、能量与金属材料概率");
        subtitle.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        subtitle.setForeground(muted);

        apiStatusLabel.setText(OpenAiCompatibleAdvisorEngine.isConfigured() ? "AI API：已配置" : "AI API：未配置，当前使用本地规则助手");
        apiStatusLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
        apiStatusLabel.setForeground(OpenAiCompatibleAdvisorEngine.isConfigured() ? new Color(22, 101, 52) : new Color(154, 52, 18));

        JPanel headline = new JPanel(new BorderLayout(10, 4));
        headline.setOpaque(false);
        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subtitle);
        headline.add(titleBox, BorderLayout.WEST);
        headline.add(apiStatusLabel, BorderLayout.EAST);

        JPanel configPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        configPanel.setOpaque(false);
        configPanel.add(fieldBlock("身份", roleCombo));
        configPanel.add(fieldBlock("采样间隔 dt / s", dtField));
        configPanel.add(fieldBlock("磁铁质量 / kg", massField));
        configPanel.add(fieldBlock("滑板角度 / °", angleField));
        configPanel.add(fieldBlock("铜板制动校准", copperBrakeField));

        JButton addDataBtn = new JButton("加入距离数据");
        JButton mockBtn = new JButton("生成模拟数据");
        JButton reportBtn = new JButton("生成结题报告");
        JButton clearBtn = new JButton("清空实验");
        stylePrimaryButton(sendBtn, primary);
        stylePrimaryButton(addDataBtn, primary);
        styleSecondaryButton(mockBtn);
        styleSecondaryButton(reportBtn);
        styleSecondaryButton(clearBtn);

        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setOpaque(false);
        inputPanel.add(fieldBlock("距离 / m", distanceField), BorderLayout.WEST);
        JPanel actionBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 18));
        actionBox.setOpaque(false);
        actionBox.add(addDataBtn);
        actionBox.add(mockBtn);
        actionBox.add(reportBtn);
        actionBox.add(clearBtn);
        inputPanel.add(actionBox, BorderLayout.CENTER);

        JPanel topPanel = cardPanel(new BorderLayout(16, 12));
        topPanel.add(headline, BorderLayout.NORTH);
        topPanel.add(configPanel, BorderLayout.CENTER);
        topPanel.add(inputPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplit.setResizeWeight(0.44);
        mainSplit.setBorder(null);
        mainSplit.setDividerSize(10);
        mainSplit.setOpaque(false);

        JPanel leftPanel = cardPanel(new BorderLayout(10, 10));
        leftPanel.add(sectionHeader("AI 对话", "回答会优先调用真实 API；未配置或网络失败时回退本地规则。"), BorderLayout.NORTH);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        chatArea.setForeground(new Color(30, 41, 59));
        chatArea.setBackground(new Color(248, 250, 252));
        chatArea.setBorder(new EmptyBorder(12, 12, 12, 12));
        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatScroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        leftPanel.add(chatScroll, BorderLayout.CENTER);

        JPanel chatInputPanel = new JPanel(new BorderLayout(8, 0));
        chatInputPanel.setOpaque(false);
        chatInput.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        chatInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                new EmptyBorder(8, 10, 8, 10)));
        chatInputPanel.add(chatInput, BorderLayout.CENTER);
        chatInputPanel.add(sendBtn, BorderLayout.EAST);
        leftPanel.add(chatInputPanel, BorderLayout.SOUTH);

        JPanel rightPanel = cardPanel(new BorderLayout(10, 10));
        rightPanel.add(sectionHeader("实时分析", "加速度曲线、能量分布和金属概率会随数据刷新。"), BorderLayout.NORTH);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        resultArea.setForeground(new Color(30, 41, 59));
        resultArea.setBackground(new Color(248, 250, 252));
        resultArea.setBorder(new EmptyBorder(10, 12, 10, 12));
        rightPanel.add(chartPanel, BorderLayout.CENTER);
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        rightPanel.add(resultScroll, BorderLayout.SOUTH);
        resultArea.setRows(12);

        mainSplit.setLeftComponent(leftPanel);
        mainSplit.setRightComponent(rightPanel);
        add(mainSplit, BorderLayout.CENTER);

        sendBtn.addActionListener(this::onSendChat);
        chatInput.addActionListener(this::onSendChat);
        addDataBtn.addActionListener(e -> onAddDistance());
        mockBtn.addActionListener(e -> onGenerateMockData());
        reportBtn.addActionListener(e -> onGenerateReport());
        clearBtn.addActionListener(e -> onClear());
    }

    private void onSendChat(ActionEvent e) {
        String text = chatInput.getText().trim();
        if (text.isEmpty()) return;
        chatInput.setText("");
        appendUser(text);
        Role role = getCurrentRole();
        syncConfigFromUi();
        setChatBusy(true);
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return advisorEngine.reply(text, role, model, knowledgeBase);
            }

            @Override
            protected void done() {
                try {
                    appendAssistant(get());
                } catch (Exception ex) {
                    appendAssistant("AI 调用失败：" + ex.getMessage() + "\n已保留本地分析结果，你可以检查 API key、网络或接口地址。");
                } finally {
                    setChatBusy(false);
                    updateResultPanel();
                }
            }
        }.execute();
    }

    private JPanel cardPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(16, 16, 16, 16)));
        return panel;
    }

    private JPanel fieldBlock(String label, JComponent input) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);
        JLabel title = new JLabel(label);
        title.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        title.setForeground(new Color(71, 85, 105));
        input.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        if (input instanceof JTextField textField) {
            textField.setColumns(8);
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(203, 213, 225)),
                    new EmptyBorder(7, 9, 7, 9)));
        }
        panel.add(title, BorderLayout.NORTH);
        panel.add(input, BorderLayout.CENTER);
        return panel;
    }

    private JPanel sectionHeader(String title, String subtitle) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 2));
        panel.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 17));
        titleLabel.setForeground(new Color(15, 23, 42));
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(100, 116, 139));
        panel.add(titleLabel);
        panel.add(subtitleLabel);
        return panel;
    }

    private void stylePrimaryButton(JButton button, Color color) {
        button.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(9, 16, 9, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button) {
        button.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        button.setForeground(new Color(15, 23, 42));
        button.setBackground(new Color(241, 245, 249));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(9, 14, 9, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setChatBusy(boolean busy) {
        sendBtn.setEnabled(!busy);
        chatInput.setEnabled(!busy);
        apiStatusLabel.setText(busy ? "AI API：正在思考..." : (OpenAiCompatibleAdvisorEngine.isConfigured() ? "AI API：已配置" : "AI API：未配置，当前使用本地规则助手"));
    }

    private String apiHint() {
        if (OpenAiCompatibleAdvisorEngine.isConfigured()) {
            return "已检测到 AI_API_KEY 或 OPENAI_API_KEY，聊天会优先使用真实 AI API。";
        }
        return "要接入真实 AI API，请设置环境变量 AI_API_KEY 或 OPENAI_API_KEY；可选设置 AI_API_URL、AI_MODEL。未设置时我会先用本地规则助手。";
    }

    private void onAddDistance() {
        try {
            syncConfigFromUi();
            double distance = Double.parseDouble(distanceField.getText().trim());

            // 这里就是给你后面平台预留的入口：
            // 你的传感器平台只要调用 addSensorDistance(distance)，就可以把数据接入程序。
            addSensorDistance(distance);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入合法数字，例如 0.125", "输入错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * ========= 给外部平台预留的核心接口 =========
     * 传感器平台每拿到一次距离数据，就调用这个方法。
     *
     * @param distanceMeters 磁铁/滑块沿滑板方向已经滑过的距离，单位：m
     */
    public void addSensorDistance(double distanceMeters) {
        ExperimentSample sample = model.addDistance(distanceMeters);
        updateResultPanel();
        chartPanel.repaint();

        if (!sample.warnings.isEmpty()) {
            appendAssistant("检测到数据异常：" + String.join("；", sample.warnings)
                    + "\n我已经对轨迹做了平滑/预测纠正，但建议你检查滑板是否有缝隙、磁铁是否偏离中心线、传感器是否抖动。");
        }
    }

    /**
     * 如果你的平台可以同时传时间戳，也可以调用这个接口。
     */
    public void addSensorDistance(double timeSeconds, double distanceMeters) {
        ExperimentSample sample = model.addDistance(timeSeconds, distanceMeters);
        updateResultPanel();
        chartPanel.repaint();
        if (!sample.warnings.isEmpty()) {
            appendAssistant("检测到数据异常：" + String.join("；", sample.warnings));
        }
    }

    private void onGenerateMockData() {
        syncConfigFromUi();
        model.clear();

        // 模拟一个“开始加速，随后被电磁阻尼压住速度”的过程。
        // 这里故意加入一个局部异常点，用来测试缝隙/轨迹异常检测。
        double s = 0;
        double v = 0;
        double dt = model.config.dt;
        Random random = new Random(7);
        for (int i = 0; i < 70; i++) {
            double t = i * dt;
            double baseA = 9.80665 * Math.sin(Math.toRadians(model.config.angleDegree));
            double damping = 2.8 * v;
            double a = baseA - damping;
            if (i > 35 && i < 40) a += 1.2; // 模拟缝隙导致制动突然减弱
            v += a * dt;
            if (v < 0) v = 0;
            s += v * dt;
            double noise = (random.nextDouble() - 0.5) * 0.002;
            addSensorDistance(t, s + noise);
        }
        appendAssistant("我已经生成了一组模拟数据。你可以看右侧的加速度曲线、能量饼图，以及金属概率判断。真实实验时，把模拟数据换成传感器距离即可。");
    }

    private void onGenerateReport() {
        syncConfigFromUi();
        String report = ReportGenerator.generate(model, knowledgeBase, getCurrentRole());
        appendAssistant(report);
        JTextArea area = new JTextArea(report);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setRows(24);
        area.setColumns(70);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "实验结题报告", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onClear() {
        model.clear();
        resultArea.setText("");
        chartPanel.repaint();
        appendAssistant("实验数据已清空，可以重新开始采集。");
    }

    private void syncConfigFromUi() {
        model.config.dt = parsePositive(dtField.getText(), 0.10);
        model.config.massKg = parsePositive(massField.getText(), 0.050);
        model.config.angleDegree = parsePositive(angleField.getText(), 30.0);
        model.config.copperBrakeCalibration = parsePositive(copperBrakeField.getText(), 0.80);
    }

    private double parsePositive(String s, double defaultValue) {
        try {
            double v = Double.parseDouble(s.trim());
            if (v <= 0) return defaultValue;
            return v;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private Role getCurrentRole() {
        return "教师".equals(roleCombo.getSelectedItem()) ? Role.TEACHER : Role.STUDENT;
    }

    private void appendUser(String text) {
        chatArea.append("\n【用户】" + text + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void appendAssistant(String text) {
        chatArea.append("\n【AI实验助手】" + text + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void updateResultPanel() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== 实验实时分析 ==========").append('\n');
        sb.append("数据点数量：").append(model.samples.size()).append('\n');

        Optional<ExperimentSample> latestOpt = model.getLatestSample();
        if (latestOpt.isEmpty()) {
            sb.append("暂无数据。请先输入距离数据。\n");
            resultArea.setText(sb.toString());
            return;
        }

        ExperimentSample latest = latestOpt.get();
        sb.append("t = ").append(df3.format(latest.time)).append(" s\n");
        sb.append("原始距离 = ").append(df3.format(latest.rawDistance)).append(" m\n");
        sb.append("纠正距离 = ").append(df3.format(latest.correctedDistance)).append(" m\n");
        sb.append("速度 v = ").append(df3.format(latest.velocity)).append(" m/s\n");
        sb.append("加速度 a = ").append(df3.format(latest.acceleration)).append(" m/s²\n");
        sb.append("重力沿斜面加速度 g·sinθ = ").append(df3.format(model.gravityAlongSlope())).append(" m/s²\n");
        sb.append("电磁/摩擦综合制动比例 = ").append(df2.format(model.estimateBrakingRatio() * 100)).append("%\n");
        sb.append("估计导电率 = ").append(df2.format(model.estimateConductivityMSm())).append(" MS/m\n");

        sb.append('\n').append("========== 能量估计 ==========").append('\n');
        EnergyState energy = model.getLatestEnergy();
        sb.append("重力势能释放 Ep = ").append(df3.format(energy.potentialEnergy)).append(" J\n");
        sb.append("当前动能 Ek = ").append(df3.format(energy.kineticEnergy)).append(" J\n");
        sb.append("热/损耗能 Q ≈ ").append(df3.format(energy.heatEnergy)).append(" J\n");

        sb.append('\n').append("========== 金属概率判断 ==========").append('\n');
        List<MetalProbability> probs = knowledgeBase.estimateMetalProbabilities(model.estimateConductivityMSm());
        for (MetalProbability p : probs) {
            sb.append(p.metal.name).append("：")
                    .append(df2.format(p.probability * 100)).append("%")
                    .append("  参考导电率=").append(df2.format(p.metal.conductivityMSm)).append(" MS/m")
                    .append('\n');
        }

        if (!latest.warnings.isEmpty()) {
            sb.append('\n').append("========== 异常提醒 ==========").append('\n');
            for (String w : latest.warnings) sb.append("- ").append(w).append('\n');
        }

        resultArea.setText(sb.toString());
    }
}

enum Role {
    STUDENT,
    TEACHER
}

class ExperimentConfig {
    double dt = 0.10;
    double massKg = 0.050;
    double angleDegree = 30.0;

    /**
     * 经验校准参数：铜板实验中，制动比例大约是多少。
     * 例如：如果铜板让加速度从理论 gsinθ 降到 20%，则制动比例约 0.80。
     * 真实项目中，这个值应该用一次已知铜板实验校准。
     */
    double copperBrakeCalibration = 0.80;
}

class ExperimentModel {
    static final double G = 9.80665;
    final ExperimentConfig config = new ExperimentConfig();
    final List<ExperimentSample> samples = new ArrayList<>();

    ExperimentSample addDistance(double distanceMeters) {
        double time = samples.isEmpty() ? 0 : samples.get(samples.size() - 1).time + config.dt;
        return addDistance(time, distanceMeters);
    }

    ExperimentSample addDistance(double timeSeconds, double distanceMeters) {
        ExperimentSample sample = new ExperimentSample();
        sample.time = timeSeconds;
        sample.rawDistance = Math.max(0, distanceMeters);

        if (samples.isEmpty()) {
            sample.correctedDistance = sample.rawDistance;
            sample.velocity = 0;
            sample.acceleration = 0;
            samples.add(sample);
            return sample;
        }

        ExperimentSample prev = samples.get(samples.size() - 1);
        double dt = Math.max(1e-6, sample.time - prev.time);

        // 轨迹预测：根据上一时刻的距离、速度、加速度预测当前距离。
        double predictedDistance = prev.correctedDistance + prev.velocity * dt + 0.5 * prev.acceleration * dt * dt;

        // 平滑纠正：原始距离与预测距离融合，降低传感器跳动影响。
        double alpha = 0.72;
        sample.correctedDistance = alpha * sample.rawDistance + (1 - alpha) * predictedDistance;

        // 距离不应该倒退；若倒退，视为测量异常，采用预测值。
        if (sample.correctedDistance < prev.correctedDistance) {
            sample.correctedDistance = predictedDistance;
            sample.warnings.add("距离出现倒退，疑似传感器噪声或滑块轨迹异常");
        }

        sample.velocity = (sample.correctedDistance - prev.correctedDistance) / dt;
        sample.acceleration = (sample.velocity - prev.velocity) / dt;

        detectAnomalies(sample, prev, dt);

        samples.add(sample);
        return sample;
    }

    void detectAnomalies(ExperimentSample sample, ExperimentSample prev, double dt) {
        double gSlope = gravityAlongSlope();
        double jerk = (sample.acceleration - prev.acceleration) / dt;
        double brakeNow = estimateInstantBrakingRatio(sample.acceleration);
        double brakePrev = estimateInstantBrakingRatio(prev.acceleration);

        if (Math.abs(jerk) > Math.max(20, 4 * gSlope / Math.max(dt, 1e-6))) {
            sample.warnings.add("加速度突变过大，疑似轨迹偏移、碰撞、传感器抖动");
        }

        if (sample.velocity > 0.03 && brakePrev - brakeNow > 0.35) {
            sample.warnings.add("制动强度突然减弱，疑似金属板之间有缝隙，或磁铁离金属板距离突然变大");
        }

        if (sample.acceleration > gSlope * 1.35 && sample.velocity > 0.02) {
            sample.warnings.add("实测加速度明显超过理论重力分量，数据可能存在时间戳误差或距离跳变");
        }
    }

    double gravityAlongSlope() {
        return G * Math.sin(Math.toRadians(config.angleDegree));
    }

    double estimateInstantBrakingRatio(double acceleration) {
        double gSlope = Math.max(1e-6, gravityAlongSlope());
        double ratio = (gSlope - acceleration) / gSlope;
        return clamp(ratio, 0, 1.5);
    }

    double estimateBrakingRatio() {
        if (samples.size() < 4) return 0;
        int start = Math.max(1, samples.size() - 12);
        double sum = 0;
        int count = 0;
        for (int i = start; i < samples.size(); i++) {
            ExperimentSample s = samples.get(i);
            if (s.velocity > 0.02) {
                sum += estimateInstantBrakingRatio(s.acceleration);
                count++;
            }
        }
        return count == 0 ? 0 : clamp(sum / count, 0, 1.5);
    }

    /**
     * 用制动比例粗略估计导电率。
     * 注意：这不是严格物理反演，因为真实电磁阻尼还与磁铁强度、面积、厚度、间隙、速度有关。
     * 这个函数是给项目做“可校准估计”的接口：
     * 用已知铜板跑一组数据，得到 copperBrakeCalibration，就能把比例映射到 MS/m。
     */
    double estimateConductivityMSm() {
        double copperConductivity = 58.108;
        double brake = estimateBrakingRatio();
        double sigma = copperConductivity * brake / Math.max(0.05, config.copperBrakeCalibration);
        return clamp(sigma, 0.1, 65.0);
    }

    EnergyState getLatestEnergy() {
        if (samples.isEmpty()) return new EnergyState();
        ExperimentSample latest = samples.get(samples.size() - 1);
        double verticalDrop = latest.correctedDistance * Math.sin(Math.toRadians(config.angleDegree));
        double ep = config.massKg * G * verticalDrop;
        double ek = 0.5 * config.massKg * latest.velocity * latest.velocity;
        double heat = Math.max(0, ep - ek);
        return new EnergyState(ep, ek, heat);
    }

    Optional<ExperimentSample> getLatestSample() {
        if (samples.isEmpty()) return Optional.empty();
        return Optional.of(samples.get(samples.size() - 1));
    }

    void clear() {
        samples.clear();
    }

    static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }
}

class ExperimentSample {
    double time;
    double rawDistance;
    double correctedDistance;
    double velocity;
    double acceleration;
    final List<String> warnings = new ArrayList<>();
}

class EnergyState {
    double potentialEnergy;
    double kineticEnergy;
    double heatEnergy;

    EnergyState() {}

    EnergyState(double potentialEnergy, double kineticEnergy, double heatEnergy) {
        this.potentialEnergy = potentialEnergy;
        this.kineticEnergy = kineticEnergy;
        this.heatEnergy = heatEnergy;
    }
}

class Metal {
    final String name;
    final double conductivityMSm;
    final String note;

    Metal(String name, double conductivityMSm, String note) {
        this.name = name;
        this.conductivityMSm = conductivityMSm;
        this.note = note;
    }
}

class MetalProbability {
    final Metal metal;
    final double probability;

    MetalProbability(Metal metal, double probability) {
        this.metal = metal;
        this.probability = probability;
    }
}

class KnowledgeBase {
    final List<Metal> metals = new ArrayList<>();

    KnowledgeBase() {
        // 单位：MS/m，约等于 10^6 S/m。
        // 这些值是常温附近的参考值，实际合金、温度、热处理状态会导致明显变化。
        metals.add(new Metal("银", 63.0, "常见金属中导电率最高，但实验滑板不常用"));
        metals.add(new Metal("铜 / 紫铜", 58.108, "100% IACS 附近，电磁阻尼通常很明显"));
        metals.add(new Metal("铝", 35.0, "约 60% IACS，较轻，导电性强"));
        metals.add(new Metal("黄铜 / 铜锌合金", 15.0, "铜合金，导电率明显低于纯铜"));
        metals.add(new Metal("青铜 / 铜锡合金", 8.5, "铜合金，具体导电率随成分变化较大"));
        metals.add(new Metal("铁 / 低碳钢", 10.0, "注意铁磁性会引入额外磁吸效应，不只电磁阻尼"));
        metals.add(new Metal("不锈钢", 1.4, "导电率较低，电磁阻尼通常较弱"));
    }

    List<MetalProbability> estimateMetalProbabilities(double estimatedConductivityMSm) {
        List<MetalProbability> result = new ArrayList<>();
        double sigma = Math.max(0.1, estimatedConductivityMSm);
        double tolerance = 0.45; // log 空间容忍度，越小越“自信”
        double totalScore = 0;
        Map<Metal, Double> scores = new LinkedHashMap<>();

        for (Metal metal : metals) {
            double diff = Math.log(sigma) - Math.log(metal.conductivityMSm);
            double score = Math.exp(-(diff * diff) / (2 * tolerance * tolerance));
            scores.put(metal, score);
            totalScore += score;
        }

        for (Metal metal : metals) {
            double p = totalScore == 0 ? 0 : scores.get(metal) / totalScore;
            result.add(new MetalProbability(metal, p));
        }

        result.sort((a, b) -> Double.compare(b.probability, a.probability));
        return result;
    }
}

interface AdvisorEngine {
    String reply(String userText, Role role, ExperimentModel model, KnowledgeBase kb);
}

class RuleBasedAdvisorEngine implements AdvisorEngine {
    @Override
    public String reply(String userText, Role role, ExperimentModel model, KnowledgeBase kb) {
        String text = userText.toLowerCase(Locale.ROOT);

        if (text.contains("结题报告") || text.contains("结束") || text.contains("报告")) {
            return ReportGenerator.generate(model, kb, role);
        }

        if (text.contains("目的") || text.contains("干什么") || text.contains("研究什么")) {
            if (role == Role.TEACHER) {
                return "从教师视角，本实验可以设计成探究式教学：让学生观察磁铁在不同金属滑板上的下滑速度差异，进一步引出电磁感应、涡流、电磁阻尼和能量守恒。教学目标可以分为三层：一是会采集距离-时间数据；二是能由数据计算速度、加速度和能量；三是能用电磁阻尼强弱反推材料导电性。";
            } else {
                return "从学生视角，你可以把实验目的写成：通过测量磁铁在金属滑板上下滑的距离-时间数据，计算速度、加速度和能量变化，观察电磁阻尼现象，并尝试根据阻尼强弱判断金属材料的种类。";
            }
        }

        if (text.contains("原理") || text.contains("为什么") || text.contains("涡流") || text.contains("电磁")) {
            return "实验原理是：磁铁相对导体运动时，导体内部磁通量发生变化，会产生感应电流，也就是涡流。根据楞次定律，涡流产生的磁场会阻碍磁铁和导体之间的相对运动，所以磁铁下滑会变慢。导电率越高、磁铁越强、间隙越小、有效面积越大，电磁阻尼通常越明显。能量上看，重力势能一部分变成动能，一部分通过涡流转化为热。";
        }

        if (text.contains("金属") || text.contains("材料") || text.contains("铜") || text.contains("铝")) {
            if (model.samples.size() < 4) {
                return "现在数据点还不够。你至少输入 4 到 6 个连续距离数据，我才能根据加速度下降程度估计导电率，再输出金属概率。";
            }
            StringBuilder sb = new StringBuilder("根据当前数据估计，导电率约为 ")
                    .append(format(model.estimateConductivityMSm())).append(" MS/m。金属概率排序如下：\n");
            List<MetalProbability> probs = kb.estimateMetalProbabilities(model.estimateConductivityMSm());
            for (int i = 0; i < Math.min(4, probs.size()); i++) {
                MetalProbability p = probs.get(i);
                sb.append(i + 1).append(". ").append(p.metal.name)
                        .append("：").append(format(p.probability * 100)).append("%\n");
            }
            sb.append("注意：这是基于电磁制动强弱的反推，后面最好用已知铜板、铝板各跑一组数据进行校准。 ");
            return sb.toString();
        }

        if (text.contains("缝隙") || text.contains("轨迹") || text.contains("异常") || text.contains("纠正")) {
            return "我会用三类信号判断异常：第一，加速度是否突然尖峰；第二，制动比例是否突然下降，如果下降明显，可能是磁铁和金属板之间出现缝隙；第三，距离是否倒退或跳变，这通常是传感器抖动或轨迹偏移。纠正机制会把原始距离和上一时刻预测距离融合，得到 correctedDistance，后续计算优先使用纠正距离。";
        }

        if (text.contains("图") || text.contains("加速度") || text.contains("能量")) {
            return "右侧上半部分是加速度-时间图像，用来观察磁铁是否被电磁阻尼压住；右侧下半部分是能量饼图，包含重力势能释放、当前动能、热/损耗能。真实实验中，如果电磁阻尼越强，热/损耗能占比通常越高。";
        }

        if (text.contains("接口") || text.contains("平台") || text.contains("传感器")) {
            return "你后面的平台只需要调用 addSensorDistance(distanceMeters) 或 addSensorDistance(timeSeconds, distanceMeters)。distanceMeters 是沿滑板方向已经滑过的距离，单位是米。程序会自动计算速度、加速度、能量和金属概率。";
        }

        if (role == Role.TEACHER) {
            return "教师模式下，我建议你先让学生预测：铜板、铝板、不锈钢板哪一个会让磁铁滑得最慢？然后采集数据，用加速度图验证猜想，最后用能量饼图解释重力势能为什么没有全部变成动能。";
        } else {
            return "学生模式下，你可以按这个顺序做：先输入实验目的，再开始采集距离数据；每加入一组距离，观察加速度是否逐渐降低；最后输入“给我来个结题报告”，我会自动整理实验目的、原理、数据分析、异常判断和结论。";
        }
    }

    private String format(double v) {
        return new DecimalFormat("0.00").format(v);
    }
}

class HybridAdvisorEngine implements AdvisorEngine {
    private final AdvisorEngine apiEngine;
    private final AdvisorEngine fallbackEngine;

    HybridAdvisorEngine(AdvisorEngine apiEngine, AdvisorEngine fallbackEngine) {
        this.apiEngine = apiEngine;
        this.fallbackEngine = fallbackEngine;
    }

    @Override
    public String reply(String userText, Role role, ExperimentModel model, KnowledgeBase kb) {
        if (!OpenAiCompatibleAdvisorEngine.isConfigured()) {
            return fallbackEngine.reply(userText, role, model, kb);
        }
        try {
            return apiEngine.reply(userText, role, model, kb);
        } catch (Exception ex) {
            return fallbackEngine.reply(userText, role, model, kb)
                    + "\n\n（真实 AI API 暂时调用失败，已自动回退本地规则助手。原因：" + ex.getMessage() + "）";
        }
    }
}

class OpenAiCompatibleAdvisorEngine implements AdvisorEngine {
    private static final String DEFAULT_URL = "https://api.openai.com/v1/chat/completions";
    private static final String DEFAULT_MODEL = "gpt-4o-mini";
    private static final Pattern CONTENT_PATTERN = Pattern.compile("\"content\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    static boolean isConfigured() {
        return !env("AI_API_KEY", env("OPENAI_API_KEY", "")).isBlank();
    }

    @Override
    public String reply(String userText, Role role, ExperimentModel model, KnowledgeBase kb) {
        String apiKey = env("AI_API_KEY", env("OPENAI_API_KEY", ""));
        if (apiKey.isBlank()) {
            throw new IllegalStateException("未设置 AI_API_KEY 或 OPENAI_API_KEY");
        }

        String apiUrl = env("AI_API_URL", DEFAULT_URL);
        String modelName = env("AI_MODEL", DEFAULT_MODEL);
        String systemPrompt = buildSystemPrompt(role, model, kb);
        String body = "{"
                + "\"model\":\"" + json(modelName) + "\","
                + "\"temperature\":0.35,"
                + "\"messages\":["
                + "{\"role\":\"system\",\"content\":\"" + json(systemPrompt) + "\"},"
                + "{\"role\":\"user\",\"content\":\"" + json(userText) + "\"}"
                + "]"
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .timeout(Duration.ofSeconds(40))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IOException("HTTP " + response.statusCode() + "：" + abbreviate(response.body(), 220));
            }
            return extractAssistantText(response.body());
        } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("请求被中断", ex);
        }
    }

    private String buildSystemPrompt(Role role, ExperimentModel model, KnowledgeBase kb) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一个金属滑板磁铁下滑实验 AI 助手，用中文回答，面向")
                .append(role == Role.TEACHER ? "教师" : "学生")
                .append("。回答要结合当前实验数据，准确、简洁、可操作。不要编造传感器不存在的数据。\n");
        sb.append("当前配置：dt=").append(model.config.dt)
                .append(" s，质量=").append(model.config.massKg)
                .append(" kg，角度=").append(model.config.angleDegree)
                .append(" 度，铜板制动校准=").append(model.config.copperBrakeCalibration)
                .append("。\n");
        sb.append("当前数据点数=").append(model.samples.size())
                .append("，估计导电率=").append(String.format(Locale.ROOT, "%.2f", model.estimateConductivityMSm()))
                .append(" MS/m。\n");
        model.getLatestSample().ifPresent(sample -> sb.append("最新数据：t=")
                .append(String.format(Locale.ROOT, "%.3f", sample.time))
                .append(" s，距离=")
                .append(String.format(Locale.ROOT, "%.3f", sample.correctedDistance))
                .append(" m，速度=")
                .append(String.format(Locale.ROOT, "%.3f", sample.velocity))
                .append(" m/s，加速度=")
                .append(String.format(Locale.ROOT, "%.3f", sample.acceleration))
                .append(" m/s^2。\n"));
        List<MetalProbability> probs = kb.estimateMetalProbabilities(model.estimateConductivityMSm());
        if (!probs.isEmpty()) {
            sb.append("金属概率前三：");
            for (int i = 0; i < Math.min(3, probs.size()); i++) {
                MetalProbability p = probs.get(i);
                if (i > 0) sb.append("；");
                sb.append(p.metal.name).append(" ")
                        .append(String.format(Locale.ROOT, "%.1f%%", p.probability * 100));
            }
            sb.append("。\n");
        }
        sb.append("如果用户要报告，可以给出结构化报告；如果用户问 API 或传感器接入，说明 addSensorDistance(distanceMeters) 和 addSensorDistance(timeSeconds, distanceMeters) 这两个入口。");
        return sb.toString();
    }

    private String extractAssistantText(String body) throws IOException {
        Matcher matcher = CONTENT_PATTERN.matcher(body);
        if (matcher.find()) {
            return unescapeJson(matcher.group(1)).trim();
        }
        throw new IOException("响应中没有找到 assistant content：" + abbreviate(body, 220));
    }

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }

    private static String json(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }

    private static String unescapeJson(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != '\\' || i == text.length() - 1) {
                sb.append(c);
                continue;
            }
            char next = text.charAt(++i);
            switch (next) {
                case '"' -> sb.append('"');
                case '\\' -> sb.append('\\');
                case '/' -> sb.append('/');
                case 'b' -> sb.append('\b');
                case 'f' -> sb.append('\f');
                case 'n' -> sb.append('\n');
                case 'r' -> sb.append('\r');
                case 't' -> sb.append('\t');
                case 'u' -> {
                    if (i + 4 < text.length()) {
                        String hex = text.substring(i + 1, i + 5);
                        sb.append((char) Integer.parseInt(hex, 16));
                        i += 4;
                    }
                }
                default -> sb.append(next);
            }
        }
        return sb.toString();
    }

    private static String abbreviate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() <= maxLength ? text : text.substring(0, maxLength) + "...";
    }
}

class ReportGenerator {
    static String generate(ExperimentModel model, KnowledgeBase kb, Role role) {
        DecimalFormat df3 = new DecimalFormat("0.000");
        DecimalFormat df2 = new DecimalFormat("0.00");

        String roleText = role == Role.TEACHER ? "教师" : "学生";
        int n = model.samples.size();
        EnergyState energy = model.getLatestEnergy();
        double conductivity = model.estimateConductivityMSm();
        List<MetalProbability> probs = kb.estimateMetalProbabilities(conductivity);
        MetalProbability top = probs.isEmpty() ? null : probs.get(0);

        double maxV = 0, minA = Double.POSITIVE_INFINITY, maxA = Double.NEGATIVE_INFINITY;
        int warningCount = 0;
        for (ExperimentSample s : model.samples) {
            maxV = Math.max(maxV, s.velocity);
            minA = Math.min(minA, s.acceleration);
            maxA = Math.max(maxA, s.acceleration);
            warningCount += s.warnings.size();
        }
        if (n == 0) {
            minA = 0;
            maxA = 0;
        }

        String metalConclusion = top == null
                ? "目前数据不足，暂时无法判断金属材料。"
                : "根据当前反演导电率，最可能的金属为“" + top.metal.name + "”，置信概率约为 " + df2.format(top.probability * 100) + "% 。";

        return "【金属滑板磁铁下滑实验结题报告】\n\n"
                + "一、实验身份与实验目的\n"
                + "本次实验以“" + roleText + "”视角进行分析，研究对象为磁铁或磁性滑块在金属滑板上下滑时的运动过程。实验目的主要包括：第一，通过传感器采集滑块下滑距离数据，建立距离—时间关系；第二，根据连续距离数据计算速度、加速度等运动学物理量；第三，结合重力势能、动能和损耗能的变化，分析磁铁下滑过程中能量转化情况；第四，利用电磁阻尼强弱反推出金属材料的可能导电率，并与知识库中的典型金属导电率进行对比，尝试判断滑板材料。\n\n"
                + "二、实验原理\n"
                + "当磁铁沿金属板运动时，金属板内部的磁通量会随时间变化，从而产生感应电流，即涡流。根据楞次定律，涡流产生的磁场会阻碍磁铁与金属板之间的相对运动，因此滑块会受到与运动方向相反的电磁阻力。导电率越高的金属，通常越容易产生较强涡流，电磁阻尼越明显。若滑板为铜或铝等高导电率材料，磁铁下滑速度一般会明显降低；若滑板为不锈钢等低导电率材料，阻尼效果则相对较弱。同时，滑块运动过程中重力势能不会全部转化为动能，其中一部分会通过电磁阻尼和摩擦等方式转化为热。\n\n"
                + "三、数据处理与物理量计算\n"
                + "本次程序共记录有效数据点 " + n + " 个。程序首先对原始距离数据进行平滑纠正，得到 correctedDistance，以减少传感器抖动、距离跳变和轨迹偏移带来的误差。随后由相邻时刻距离差计算速度，由相邻速度差计算加速度。当前实验设置中，磁铁质量为 " + df3.format(model.config.massKg) + " kg，滑板角度为 " + df2.format(model.config.angleDegree) + "°，理论重力沿斜面分量为 " + df3.format(model.gravityAlongSlope()) + " m/s²。实验过程中最大速度约为 " + df3.format(maxV) + " m/s，加速度范围约为 " + df3.format(minA) + " 到 " + df3.format(maxA) + " m/s²。\n\n"
                + "四、能量分析\n"
                + "根据当前最后一个数据点，程序估计重力势能释放量约为 " + df3.format(energy.potentialEnergy) + " J，滑块当前动能约为 " + df3.format(energy.kineticEnergy) + " J，热或综合损耗能约为 " + df3.format(energy.heatEnergy) + " J。若能量饼图中热/损耗能占比较高，说明下滑过程中有较多机械能没有转化为动能，而是被电磁阻尼或摩擦消耗。对于磁铁—金属板实验来说，这一现象正是涡流阻尼存在的重要证据。\n\n"
                + "五、金属材料判断\n"
                + "程序根据实际加速度与理论重力沿斜面加速度之间的差异，估计电磁/摩擦综合制动比例，并进一步换算出等效导电率。本次估计导电率约为 " + df2.format(conductivity) + " MS/m。" + metalConclusion + " 需要注意的是，该判断属于概率推断，而不是绝对鉴定。真实导电率反演还会受到磁铁磁场强度、金属板厚度、磁铁与金属板间隙、滑块速度、轨迹是否居中、温度等因素影响。因此，正式实验中建议先使用已知铜板和已知铝板进行标定，再判断未知金属。\n\n"
                + "六、异常检测与纠正\n"
                + "本程序设置了轨迹纠正和异常检测机制。当距离数据出现倒退、加速度突变、制动强度突然减弱时，程序会给出提醒。其中，制动强度突然减弱可能代表磁铁与金属板之间出现缝隙，或者滑块偏离金属板中心区域；加速度尖峰可能代表碰撞、传感器抖动或数据采样间隔不稳定。本次实验累计异常提示数量为 " + warningCount + " 条。对于异常数据，程序会使用上一时刻的运动状态预测当前位置，并与原始测量值融合，从而得到更平滑的纠正距离。\n\n"
                + "七、实验结论\n"
                + "综合来看，本实验能够通过距离传感器数据完成运动学分析、能量分析、金属材料概率判断和异常轨迹检测。实验结果表明，磁铁在金属板上下滑时不仅受到重力作用，还会受到与金属导电性有关的电磁阻尼作用。后续若要提高判断准确率，应增加采样频率，记录多次重复实验结果，并使用已知材料进行标定。若从教学角度使用，本实验可以很好地把力学、电磁学、能量守恒和数据分析结合起来；若从学生探究角度使用，则可以围绕“不同金属为什么会让磁铁下滑速度不同”展开实验设计与讨论。";
    }
}

class ExperimentChartPanel extends JPanel {
    private final ExperimentModel model;
    private final KnowledgeBase kb;
    private final DecimalFormat df2 = new DecimalFormat("0.00");
    private final Color ink = new Color(15, 23, 42);
    private final Color muted = new Color(100, 116, 139);
    private final Color lineBlue = new Color(14, 116, 144);
    private final Color warnOrange = new Color(234, 88, 12);

    ExperimentChartPanel(ExperimentModel model, KnowledgeBase kb) {
        this.model = model;
        this.kb = kb;
        setPreferredSize(new Dimension(680, 460));
        setBackground(new Color(248, 250, 252));
        setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int topH = (int) (h * 0.58);
        drawAccelerationChart(g2, 22, 24, w - 44, topH - 42);
        drawEnergyPie(g2, 22, topH + 14, w - 44, h - topH - 38);
    }

    private void drawAccelerationChart(Graphics2D g2, int x, int y, int w, int h) {
        drawPanelBackground(g2, x, y, w, h);
        g2.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 15));
        g2.setColor(ink);
        g2.drawString("加速度 - 时间", x + 16, y + 26);

        if (model.samples.size() < 2) {
            g2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
            g2.setColor(muted);
            g2.drawString("暂无足够数据，先加入距离数据或生成模拟数据。", x + 18, y + 56);
            return;
        }

        double minT = model.samples.get(0).time;
        double maxT = model.samples.get(model.samples.size() - 1).time;
        double minA = Double.POSITIVE_INFINITY;
        double maxA = Double.NEGATIVE_INFINITY;
        for (ExperimentSample s : model.samples) {
            minA = Math.min(minA, s.acceleration);
            maxA = Math.max(maxA, s.acceleration);
        }
        if (Math.abs(maxA - minA) < 1e-6) {
            maxA += 1;
            minA -= 1;
        }

        int pad = 46;
        int px0 = x + pad;
        int py0 = y + h - 34;
        int px1 = x + w - 22;
        int py1 = y + 48;

        g2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        g2.setColor(new Color(203, 213, 225));
        for (int i = 0; i <= 4; i++) {
            int gy = py0 - (py0 - py1) * i / 4;
            g2.drawLine(px0, gy, px1, gy);
        }
        g2.setColor(muted);
        g2.drawLine(px0, py0, px1, py0);
        g2.drawLine(px0, py0, px0, py1);
        g2.drawString("t/s", px1 - 20, py0 + 20);
        g2.drawString("a", px0 - 25, py1 + 5);
        g2.drawString(df2.format(minA), x + 5, py0);
        g2.drawString(df2.format(maxA), x + 5, py1 + 10);

        g2.setStroke(new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(lineBlue);
        int lastX = -1, lastY = -1;
        for (ExperimentSample s : model.samples) {
            int px = map(s.time, minT, maxT, px0, px1);
            int py = map(s.acceleration, minA, maxA, py0, py1);
            if (lastX != -1) g2.drawLine(lastX, lastY, px, py);
            g2.fillOval(px - 2, py - 2, 4, 4);
            lastX = px;
            lastY = py;
        }

        g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{6f, 6f}, 0f));
        g2.setColor(warnOrange);
        double gSlope = model.gravityAlongSlope();
        int gy = map(gSlope, minA, maxA, py0, py1);
        g2.drawLine(px0, gy, px1, gy);
        g2.drawString("理论 g·sinθ", px1 - 90, gy - 4);
        g2.setStroke(new BasicStroke(1f));
    }

    private void drawEnergyPie(Graphics2D g2, int x, int y, int w, int h) {
        drawPanelBackground(g2, x, y, w, h);
        g2.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 15));
        g2.setColor(ink);
        g2.drawString("能量分布与金属初判", x + 16, y + 26);

        EnergyState e = model.getLatestEnergy();
        double ep = Math.max(0, e.potentialEnergy);
        double ek = Math.max(0, e.kineticEnergy);
        double heat = Math.max(0, e.heatEnergy);
        double sum = ep + ek + heat;
        if (sum <= 1e-9) {
            g2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
            g2.setColor(muted);
            g2.drawString("暂无足够能量数据。", x + 18, y + 56);
            return;
        }

        int size = Math.min(h - 58, 168);
        int cx = x + 30;
        int cy = y + 46;

        double[] values = {ep, ek, heat};
        String[] names = {"重力势能释放", "当前动能", "热/损耗能"};
        Color[] colors = {
                new Color(92, 140, 210),
                new Color(95, 170, 110),
                new Color(220, 145, 70)
        };

        int startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            int angle = (int) Math.round(values[i] / sum * 360);
            g2.setColor(colors[i]);
            g2.fillArc(cx, cy, size, size, startAngle, angle);
            startAngle += angle;
        }
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(cx, cy, size, size);
        g2.setStroke(new BasicStroke(1f));

        int lx = cx + size + 35;
        int ly = cy + 20;
        g2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
        for (int i = 0; i < values.length; i++) {
            g2.setColor(colors[i]);
            g2.fillRoundRect(lx, ly + i * 28, 14, 14, 5, 5);
            g2.setColor(ink);
            double percent = values[i] / sum * 100;
            g2.drawString(names[i] + "：" + df2.format(values[i]) + " J，" + df2.format(percent) + "%", lx + 22, ly + 12 + i * 28);
        }

        double sigma = model.estimateConductivityMSm();
        List<MetalProbability> probs = kb.estimateMetalProbabilities(sigma);
        if (!probs.isEmpty()) {
            MetalProbability top = probs.get(0);
            g2.setColor(lineBlue);
            g2.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 13));
            g2.drawString("金属初判：" + top.metal.name + "，概率约 " + df2.format(top.probability * 100) + "%", lx, ly + 105);
            g2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
            g2.setColor(muted);
            g2.drawString("估计导电率：" + df2.format(sigma) + " MS/m", lx, ly + 128);
        }
    }

    private void drawPanelBackground(Graphics2D g2, int x, int y, int w, int h) {
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, w, h, 14, 14);
        g2.setColor(new Color(226, 232, 240));
        g2.drawRoundRect(x, y, w, h, 14, 14);
    }

    private int map(double v, double inMin, double inMax, int outMin, int outMax) {
        if (Math.abs(inMax - inMin) < 1e-9) return (outMin + outMax) / 2;
        double t = (v - inMin) / (inMax - inMin);
        return (int) Math.round(outMin + t * (outMax - outMin));
    }
}
