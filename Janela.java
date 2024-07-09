import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Janela extends JFrame implements ActionListener {
    private JTextField campoCodinome;
    private JTextField campoQuantidade;
    private JTextField campoLatitude;
    private JTextField campoLongitude;
    private JButton cadastrar;
    private JButton listar;
    private JTextArea area;
    private JButton fechar;
    private JButton limpar;
    private Cadastro cadastro;
    private JScrollPane barra;

    public Janela() {
        super("Cadastrar Equipe");
        setLookAndFeel();

        cadastro = new Cadastro();

        JPanel textop = new JPanel(new GridLayout(4, 2, 10, 10));
        textop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textop.add(new JLabel("Codinome:"));
        campoCodinome = new JTextField(30);
        textop.add(campoCodinome);

        textop.add(new JLabel("Quantidade de membros:"));
        campoQuantidade = new JTextField(30);
        textop.add(campoQuantidade);

        textop.add(new JLabel("Latitude:"));
        campoLatitude = new JTextField(30);
        textop.add(campoLatitude);

        textop.add(new JLabel("Longitude:"));
        campoLongitude = new JTextField(30);
        textop.add(campoLongitude);

        cadastrar = new JButton("Cadastrar equipe");
        limpar = new JButton("Limpar");
        listar = new JButton("Listar equipes cadastradas");
        fechar = new JButton("Fechar");

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botoesPanel.add(cadastrar);
        botoesPanel.add(limpar);
        botoesPanel.add(listar);
        botoesPanel.add(fechar);

        area = new JTextArea(20, 40);
        area.setEditable(false);
        barra = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(10, 10));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.add(textop, BorderLayout.NORTH);
        container.add(botoesPanel, BorderLayout.CENTER);
        container.add(barra, BorderLayout.SOUTH);

        cadastrar.addActionListener(this);
        limpar.addActionListener(this);
        listar.addActionListener(this);
        fechar.addActionListener(this);

        this.add(container);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrar) {
            CadastrarEquipe();
        } else if (e.getSource() == fechar) {
            System.exit(0);
        } else if (e.getSource() == listar) {
            area.setText(ListarEquipes());
        } else if (e.getSource() == limpar) {
            limparCampos();
        }
    }
    public void CadastrarEquipe() {
        try {
            String codinome = campoCodinome.getText();
            if (codinome.isEmpty()) {
                area.setText("O codinome não pode estar vazio");
            } else {
                int quantidade = Integer.parseInt(campoQuantidade.getText());
                double longitude = Double.parseDouble(campoLongitude.getText());
                double latitude = Double.parseDouble(campoLatitude.getText());
                Equipe eq = new Equipe(codinome, quantidade, longitude, latitude);
                if (cadastro.addEquipe(eq)) {
                    area.setText("Equipe cadastrada com sucesso!");
                } else {
                    area.setText("Equipe com esse codinome já existe.");
                }
            }
        } catch (NumberFormatException ex) {
            area.setText("Erro ao cadastrar: Os dados são inválidos!");
        }
    }
    public void limparCampos() {
        campoCodinome.setText("");
        campoQuantidade.setText("");
        campoLatitude.setText("");
        campoLongitude.setText("");
    }
    public String ListarEquipes() {
        StringBuilder descricao = new StringBuilder();
        if (cadastro.clonarEquipes().isEmpty()) {
            return "Nenhuma equipe foi cadastrada!";
        }
        for (int i = 0; i < cadastro.clonarEquipes().size(); i++) {
            Equipe eq = cadastro.clonarEquipes().get(i);
            descricao.append("\nEquipe n°").append(i + 1).append("\nCodinome: ").append(eq.getCodinome())
                    .append("\nQuantidade: ").append(eq.getQuantidade())
                    .append("\nLatitude: ").append(eq.getLatitude())
                    .append("\nLongitude: ").append(eq.getLongitude());
        }
        return descricao.toString();
    }
}