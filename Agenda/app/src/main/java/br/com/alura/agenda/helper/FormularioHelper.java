package br.com.alura.agenda.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.alura.agenda.activity.FormularioActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by root on 21/04/17.
 */

public class FormularioHelper {

    private FormularioActivity activity;
    private Aluno aluno = new Aluno();

    /**Campos do formulário*/
    private EditText nome = null;
    private EditText endereco = null;
    private EditText telefone  = null;
    private EditText site = null;
    private RatingBar nota = null;
    private ImageView formularioFoto = null;

    public FormularioHelper(FormularioActivity activity){
        this.activity = activity;

        this.nome = (EditText)activity.findViewById(R.id.formulario_nome);
        this.endereco = (EditText)activity.findViewById(R.id.formulario_endereco);
        this.telefone  = (EditText)activity.findViewById(R.id.formulario_telefone);
        this.site = (EditText)activity.findViewById(R.id.formulario_site);
        this.nota = (RatingBar)activity.findViewById(R.id.formulario_nota);
        this.formularioFoto = (ImageView)activity.findViewById(R.id.formulario_foto);
    }

    /**
     * Método responsável por retornar um aluno com os dados que foram preenchidos no formulário
     * @return Aluno
     * */
    public Aluno getAluno(){

        this.aluno.setNome(this.nome.getText().toString());
        this.aluno.setEndereco(this.endereco.getText().toString());
        this.aluno.setTelefone(this.telefone.getText().toString());
        this.aluno.setSite(this.site.getText().toString());
        this.aluno.setNota(new Double(this.nota.getRating()));
        this.aluno.setCaminhoFoto((String) this.formularioFoto.getTag());
        return this.aluno;
    }

    /**
     * Método responsável por preencher o formulário com os dados do aluno que será editado
     * @param aluno
     * */
    public void preencheFormulario(Aluno aluno){

        this.aluno = aluno;
        this.nome.setText(aluno.getNome());
        this.endereco.setText(aluno.getEndereco());
        this.telefone.setText(aluno.getTelefone());
        this.site.setText(aluno.getSite());
        this.nota.setRating(aluno.getNota().floatValue());
        carregaImagem(aluno.getCaminhoFoto(), formularioFoto);
    }

    public static void carregaImagem(String caminhoFoto, ImageView foto) {
        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            bitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);
            foto.setImageBitmap(bitmap);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            foto.setTag(caminhoFoto);
        }
    }

    public void carregaImagem(String caminhoFoto){
        carregaImagem(caminhoFoto, formularioFoto);
    }
}
