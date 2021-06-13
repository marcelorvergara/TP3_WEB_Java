package edu.infnet;

import edu.infnet.cep.model.Endereco;
import edu.infnet.cep.service.BuscaCepSvc;
import edu.infnet.model.Usuario;
import edu.infnet.persistence.service.UserService;
import edu.infnet.s3.service.AwsS3Service;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@Controller
public class ControllerClass {

    private static final Logger log = LoggerFactory.getLogger(BasicApplication.class);

    //cep
    @Autowired
    public BuscaCepSvc buscaCepSvc;

    //S3
    @Autowired
    private AwsS3Service awsS3Service;

    @Value("${aws.bucketName}")
    private String bucket_name;

    //Jpa
    @Autowired
    private UserService userService;

    //Controllers
    @GetMapping("/")
    public String index(Model model) {

        List<Usuario> usuarios = userService.findAll();

        model.addAttribute("usuariosLst", usuarios);

        return "index";
    }

    @GetMapping("/novo_user")
    public String novoUser(Model model) {

        Usuario user = new Usuario();

        model.addAttribute("usuario", user);

        return "novo_user";
    }

    //buscar cep
    @RequestMapping(value = "/novo_user_cep", method = RequestMethod.POST, params = "action=buscaCep")
    public String buscarCep(@RequestParam("cep") String buscaCep,
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("file") MultipartFile multipartFile,
            Model model) {

        log.info("buscar cep " + buscaCep);

        Endereco endereco = buscaCepSvc.buscaEnderecoPor(buscaCep);
        log.info("Endereço: " + endereco.getLogradouro());
        log.info("Bairro: " + endereco.getBairro());
        log.info("Localidade: " + endereco.getLocalidade());
        log.info("Estado: " + endereco.getUf());

        Usuario user = new Usuario(nome, email, phone, buscaCep, endereco.getLogradouro(), endereco.getBairro(), endereco.getLocalidade(), endereco.getUf(), multipartFile.toString());

        model.addAttribute("usuario", user);

        return "novo_user_cep";
    }

    //enviar foto
    @PostMapping(value = "/novo_user_final", params = "action=upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("cep") String buscaCep,
            @RequestParam("nome") String nome, @RequestParam("email") String email, @RequestParam("phone") String phone,
            @RequestParam("cep") String cep, @RequestParam("endereco") String endereco, @RequestParam("bairro") String bairro,
            @RequestParam("localidade") String localidade, @RequestParam("uf") String uf, Model model) throws IOException {

        //já subir o arquivo para o s3 e usar a url do bucket para guardar na base de dados
        File file = File.createTempFile("tmp", "tmp");
        multipartFile.transferTo(file);

        log.info("Foto file: " + multipartFile.getOriginalFilename());

        awsS3Service.upload(file, multipartFile.getOriginalFilename(), bucket_name);

        StringBuilder fileUrl = new StringBuilder();

        fileUrl.append("https://dr4s3bucket.s3.sa-east-1.amazonaws.com/").append(multipartFile.getOriginalFilename());

        Usuario user = new Usuario(nome, email, phone, cep, endereco, bairro, localidade, uf, fileUrl.toString());

        model.addAttribute("usuario", user);

        return "novo_user_gravar";
    }

    //gravar no db
    @PostMapping(value = "/novo_user_gravar")
    public String criar(@RequestParam("nome") String nome, @RequestParam("email") String email, @RequestParam("phone") String phone,
            @RequestParam("cep") String cep, @RequestParam("endereco") String endereco, @RequestParam("bairro") String bairro,
            @RequestParam("localidade") String localidade, @RequestParam("uf") String uf, @RequestParam("foto") String foto) {

        log.info("user foto: " + foto);

        Usuario user = new Usuario(nome, email, phone, cep, endereco, bairro, localidade, uf, foto);

        userService.save(user);

        return "redirect:/";
    }

    //editar usuario
    @GetMapping("/editar/{id}")
    public String alterarUsuario(@PathVariable Long id, Model model) throws NotFoundException {

        log.info("id user: " + id);

        Usuario user = userService.findById(id)
                .orElseThrow(() -> new javassist.NotFoundException(id.toString()));

        log.info("cep: " + user.getCep());

        model.addAttribute("usuario", user);

        return "editar_1";
    }

    @PostMapping("/editar")
    public String gravarAlterUser(@RequestParam("id") String id, @RequestParam("nome") String nome, @RequestParam("email") String email, @RequestParam("phone") String phone,
            @RequestParam("cep") String cep, @RequestParam("endereco") String endereco, @RequestParam("bairro") String bairro,
            @RequestParam("localidade") String localidade, @RequestParam("uf") String uf,
            @RequestParam("foto") String foto,
            @RequestParam("file") MultipartFile multipartFile) throws IOException, NotFoundException {

        StringBuilder fileUrl = new StringBuilder();

        //se tiver alteração de foto na edição do usuário
        if (!multipartFile.getOriginalFilename().equals("")) {

            File file = File.createTempFile("tmp", "tmp");
            multipartFile.transferTo(file);

            awsS3Service.upload(file, multipartFile.getOriginalFilename(), bucket_name);

            fileUrl.append("https://dr4s3bucket.s3.sa-east-1.amazonaws.com/").append(multipartFile.getOriginalFilename());

            foto = fileUrl.toString();
        }

        //atualizar o usuário no banco de dados
        Long idLong = Long.parseLong(id);

        Usuario user;
        user = userService.findById(idLong)
                .orElseThrow(() -> new javassist.NotFoundException(idLong.toString()));

        user.setNome(nome);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCep(cep);
        user.setEndereco(endereco);
        user.setBairro(bairro);
        user.setLocalidade(localidade);
        user.setUf(uf);
        user.setFoto(foto);

        userService.save(user);

        log.info("Usuário atualizado com sucesso: " + user.getNome());
        return "redirect:/";
    }

}
