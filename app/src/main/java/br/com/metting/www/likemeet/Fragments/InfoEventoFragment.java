package br.com.metting.www.likemeet.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.metting.www.likemeet.Class.Categoria;
import br.com.metting.www.likemeet.R;


public class InfoEventoFragment extends Fragment {
    private CheckBox checkBoxtaxaEntrada;
    private CheckBox checkBoxRestricaoIdade;
    private CheckBox checkBoxFluxoPessoas;
    private EditText editTextTaxaEntrada;
    private CheckBox checkBoxPrivado;
    private EditText editTextRestricaoIdade;
    private EditText editTextFluxoPessoas;
    private EditText editTextNome;
    private EditText editTextDescricao;
    private int privado;
    private Spinner spinner;
    private ArrayAdapter<String> opcoesSpinner;

    //variavel do menu swep
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info_evento, container, false);

        checkBoxtaxaEntrada = (CheckBox) view.findViewById(R.id.checkBoxTaxaEntrada);
        checkBoxRestricaoIdade = (CheckBox) view.findViewById(R.id.checkBoxRestricaoIdade);
        checkBoxFluxoPessoas = (CheckBox) view.findViewById(R.id.checkBoxFluxoPessoas);
        spinner = (Spinner) view.findViewById(R.id.spinnerCategoria);
        editTextNome = (EditText) view.findViewById(R.id.editTextNome);
        editTextDescricao = (EditText) view.findViewById(R.id.editTextDescricao);

        editTextTaxaEntrada = (EditText) view.findViewById(R.id.editTextTaxaEntrada);
        editTextRestricaoIdade = (EditText) view.findViewById(R.id.editTextRestricaoIdade);
        editTextFluxoPessoas = (EditText) view.findViewById(R.id.editTextFluxoPessoas);
        checkBoxPrivado = (CheckBox) view.findViewById(R.id.checkBoxPrivado);


        // acoes ao alterar o foco
        editTextNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) verificarNome();
            }
        });
        editTextDescricao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) verificarDescricao();
            }
        });
        editTextTaxaEntrada.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) verificarTaxaDeEntrada();
            }
        });
        editTextRestricaoIdade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) verificarIdade();
            }
        });
        editTextFluxoPessoas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) verificarFluxo();
            }
        });
        // spinner categorias
        // definindo spinner
        // definindo spinner
        opcoesSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        // define onde vai ficar os itens do spinner
        opcoesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(opcoesSpinner);

        for (Categoria lCategoria : Categoria.getLista()) {
            opcoesSpinner.add(lCategoria.getNome());
        }

        checkBoxFluxoPessoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxFluxoPessoas.isChecked()) {
                    editTextFluxoPessoas.setVisibility(View.VISIBLE);
                } else {
                    editTextFluxoPessoas.setText("");
                    editTextFluxoPessoas.setVisibility(View.INVISIBLE);
                }
                ;
            }
        });

        checkBoxRestricaoIdade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxRestricaoIdade.isChecked()) {
                    editTextRestricaoIdade.setVisibility(View.VISIBLE);
                } else {
                    editTextRestricaoIdade.setText("");
                    editTextRestricaoIdade.setVisibility(View.INVISIBLE);
                }
                ;
            }
        });

        checkBoxtaxaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxtaxaEntrada.isChecked()) {
                    editTextTaxaEntrada.setVisibility(View.VISIBLE);
                } else {
                    editTextTaxaEntrada.setText("");
                    editTextTaxaEntrada.setVisibility(View.INVISIBLE);
                }
                ;
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    // veriicar se os campos estao corretos
    /*
    *
    * *
    * *
    * *
    * ****
    * *
    * **
     */
    public boolean verificarNome() {
        if (editTextNome.getText().toString().contains("          ")) {
            editTextNome.setError("Muitos espaços em branco para o nome!");
            return false;
        }
        if (editTextNome.getText().toString().trim().equals("")) {
            editTextNome.setError("Informe um nome para o evento!");
            return false;
        }
        if (editTextNome.getText().toString().length() > 50) {
            editTextNome.setError("O nome pode conter apenas 50 caracteres!");
            return false;
        }
        return true;
    }

    public boolean verificarDescricao() {
        if (editTextDescricao.getText().toString().contains("          ")) {
            editTextDescricao.setError("Muitos espaços em branco para a descrição!");
            return false;
        }
        if (editTextDescricao.getText().toString().trim().equals("")) {
            editTextDescricao.setError("Informe uma descrição para o evento!");
            return false;
        }
        if (editTextDescricao.getText().toString().length() > 100) {
            editTextDescricao.setError("A descrição pode conter apenas 100 caracteres!");
            return false;
        }
        return true;
    }

    public boolean verificarTaxaDeEntrada() {
        if (checkBoxtaxaEntrada.isChecked()) {
            try {
                Double taxaEntrada = Double.parseDouble(editTextTaxaEntrada.getText().toString());

                if (taxaEntrada < 0) {
                    editTextTaxaEntrada.setError("Digite uma taxa válida!");
                    return false;
                }
                if (taxaEntrada > 100000) {
                    editTextTaxaEntrada.setError("Digite uma taxa válida!");
                    return false;
                }
            } catch (Exception e) {
                editTextTaxaEntrada.setError("Digite uma taxa válida!");
                return false;
            }
        }
        return true;
    }
    public boolean verificarIdade() {
        if (checkBoxRestricaoIdade.isChecked()) {
            try {
                Integer idade = Integer.parseInt(editTextRestricaoIdade.getText().toString());

                if (idade < 0 || idade > 150) {
                    editTextRestricaoIdade.setError("Informe uma idade válida!");
                    return false;
                }

            } catch (Exception e) {
                editTextRestricaoIdade.setError("Informe uma idade válida!");
                return false;
            }
        }
        return true;
    }
    public boolean verificarFluxo() {
        if (checkBoxFluxoPessoas.isChecked()) {
            try {
                Integer fluxo = Integer.parseInt(editTextFluxoPessoas.getText().toString());

                if (fluxo < 0 || fluxo > 1000000) {
                    editTextFluxoPessoas.setError("Informe uma quantidade válida!");
                    return false;
                }

            } catch (Exception e) {
                editTextFluxoPessoas.setError("Informe uma quantidade válida!");
                return false;
            }
        }
        return true;
    }

    // veriicar se os campos estao corretos
//fIM

    public EditText getEditTextTaxaEntrada() {
        return editTextTaxaEntrada;
    }

    public EditText getEditTextRestricaoIdade() {
        return editTextRestricaoIdade;
    }

    public EditText getEditTextFluxoPessoas() {
        return editTextFluxoPessoas;
    }

    public EditText getEditTextDescricao() {
        return editTextDescricao;
    }

    public String getTextDescricao() {
        return editTextDescricao.getText().toString();
    }

    public int getPrivado() {
        return privado;
    }


    public CheckBox getCheckBoxFluxoPessoas() {
        return checkBoxFluxoPessoas;
    }

    public CheckBox getCheckBoxRestricaoIdade() {
        return checkBoxRestricaoIdade;
    }

    public CheckBox getCheckBoxtaxaEntrada() {
        return checkBoxtaxaEntrada;
    }


    public String getTextFluxoPessoas() {
        return editTextFluxoPessoas.getText().toString();
    }

    public String getTextNome() {
        return editTextNome.getText().toString();
    }

    public EditText getEditTextNome() {
        return editTextNome;
    }

    public CheckBox getCheckBoxPrivado() {
        return checkBoxPrivado;
    }

    public String getTextRestricaoIdade() {
        return editTextRestricaoIdade.getText().toString();
    }

    public String getTextTaxaEntrada() {
        return editTextTaxaEntrada.getText().toString();
    }

    public String getCategoria() {
        return spinner.getSelectedItem().toString();
    }


}
