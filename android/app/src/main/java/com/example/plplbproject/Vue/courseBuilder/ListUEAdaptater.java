package com.example.plplbproject.Vue.courseBuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.plplbproject.R;
import com.example.plplbproject.controleur.UeVisualisation.UeVisualisationClickListener;
import com.example.plplbproject.controleur.courseBuilder.CourseBuilderModele;
import com.example.plplbproject.data.DataSemester;

import java.util.ArrayList;

import metier.Categorie;
import metier.UE;
import metier.parcours.Parcours;
import metier.semestre.Semestre;

/**
 * Adaptateur de la liste des categorie contenant les ue
 */
public class ListUEAdaptater extends BaseExpandableListAdapter {

    private CourseBuilderActivity vue;
    private CourseBuilderModele modele;
    private int semestreCourant;  // Pour lisibilité
    private ArrayList<Categorie> categorieArrayList = new ArrayList<Categorie>();
    private ArrayList<Categorie> originalcategorieArraylist = new ArrayList<>();
    private boolean filtered;

    /**
     * L'adapteur doit savoir quel est le semestre qu'il doit adapter
     * @param vue
     * @param modele
     */

    public ListUEAdaptater(CourseBuilderActivity vue, CourseBuilderModele modele) {
        this.vue = vue;
        this.modele = modele;
        this.semestreCourant = modele.getIndexCurrentSemester();
        if(DataSemester.SEMESTER.getNumberSemesters()> semestreCourant){
            this.categorieArrayList = DataSemester.SEMESTER.getSemesterList().get(semestreCourant).getListCategorie(); // listDataHeader
            this.originalcategorieArraylist.addAll(categorieArrayList);
        }
        this.filtered = false;
    }

    @Override
    public int getGroupCount() {
        return categorieArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categorieArrayList.get(groupPosition).getListUE().size();
    }

    @Override
    public Object getGroup(int i) {
        return categorieArrayList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return categorieArrayList.get(i).getListUE().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Adapter du parent
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Categorie c = (Categorie) getGroup(groupPosition);
        String headerTitle = c.getName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) vue.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.category_element, null);
        }

        TextView categoryName = convertView.findViewById(R.id.catName);
        categoryName.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final UE ue = (UE) getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) vue.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ue_element_main, null);
        }

        TextView ueName = (TextView) convertView.findViewById(R.id.ueName);
        final TextView ueCode = (TextView) convertView.findViewById(R.id.codeUe);
        View ueRect = (View) convertView.findViewById(R.id.rectangleUe);
        Button description = (Button) convertView.findViewById(R.id.descriptionButton);


        //Mise a jour du texte
        ueName.setText(ue.getUeName());
        ueCode.setText(ue.getUeCode());

        if(modele.getCourse()!=null) {
            if (modele.getCourse().isChecked(ue)) ueRect.setBackgroundColor(0xff51C5C4);//bleu
            else if (!modele.getCourse().canBeCheckedUE(ue))
                ueRect.setBackgroundColor(0xffD54F34);//rouge
            else ueRect.setBackgroundColor(0xff62C65B);//vert


            convertView.setOnClickListener(new UeClickListener(ue));
            description.setOnClickListener(new UeVisualisationClickListener(vue.getApplicationContext(),ue));
        }
        else{
            //comme on a pas de parcours on met en gris (ne doit pas ce produire en si l'apllication fonctionne bien
            ueRect.setBackgroundColor(0xff888888);//gris
        }
        return convertView;
    }

    /**
     * Fonction utlisée pour filtrer les Ues affichées dans les catégories
     * @param query l'input pour filtre
     */

    public void filterData(String query){

        int flag = 0;

        System.out.println(originalcategorieArraylist);

        query = query.toLowerCase();
        categorieArrayList.clear();

        if(query == "" || query.isEmpty()){
            categorieArrayList.addAll(originalcategorieArraylist);
        }

        else{
            for (Categorie c: originalcategorieArraylist
                 ) {
                ArrayList<UE> ueList = c.getListUE();
                ArrayList<UE> newUEList = new ArrayList<>();

                for (UE ue: ueList
                     ) {
                    if(ue.getUeName().toLowerCase().contains(query) || ue.getUeCode().toLowerCase().contains(query)){
                        newUEList.add(ue);
                    }

                }
                if (newUEList.size() >0){
                    Categorie newCategorie = new Categorie(c.getName(),newUEList);
                    categorieArrayList.add(newCategorie);
                    flag = 1;
                }
                if(c.getName().toLowerCase().contains(query) && flag == 0){
                    categorieArrayList.add(c);
                }
            }
        }
        filtered = true;
        notifyDataSetChanged();
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    /**
     * Permet de notifier a la liste que la liste des donner vient de changer
     */
    @Override
    public void notifyDataSetChanged(){
        this.semestreCourant = modele.getIndexCurrentSemester();
        Semestre tmp = DataSemester.SEMESTER.getSemesterList().get(semestreCourant);

        if (filtered){
            filtered = false;
        }

        // Si notifyDataSetChanged n'est pas appellé après un filtrage

        else
        {
            if(tmp==null){
                categorieArrayList = new ArrayList<Categorie>();
            }
            else{
                categorieArrayList = tmp.getListCategorie();
                if(categorieArrayList.size() > 1){
                    originalcategorieArraylist = new ArrayList<>();
                    originalcategorieArraylist.addAll(tmp.getListCategorie());
                }
                System.out.println("######" + categorieArrayList);

            }
        }
        super.notifyDataSetChanged();
    }


    /**
     * Controlleur qui s'occupe de gerer les click sur une ue
     */
    public class UeClickListener implements View.OnClickListener{

        private UE ue;


        public UeClickListener(UE ue) {
            this.ue = ue;
        }

        @Override
        public void onClick(View view) {
            Parcours course = modele.getCourse();
            if(!modele.getCourse().isChecked(ue) && course.canBeCheckedUE(ue)){ //Change le check de l'ue en consequence.
                course.checkUE(ue);
            }
            else if(course.canBeUncheckedUE(ue)){
                course.uncheckUE(ue);
            }
            //Un changement a eu lieu.
            //vue.needSave(true);
            vue.notifyUeListView();
            vue.updateInformationCourse();

        }
    }
}
