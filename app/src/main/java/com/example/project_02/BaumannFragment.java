package com.example.project_02;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BaumannFragment extends Fragment {

    ArrayList<checkVO> list = new ArrayList<>(); // 문제 항목 담아줄 ArrayList
    double score, scoreoil, scoresen, scoremel, scoretin;
    static double scoreavg;// 바우만 타입별 점수
    String mbtiDO, mbtiSR, mbtiPN, mbtiWT; // 바우만 타입
    boolean temp; // 질문 5개 ,4개 구분해서 비져블 인비져블 구분할 변수
    boolean isChecked; // 항목 체크 될 때 안될 때 구분할 변수

    TextView type, Qs; // 피부종목 type, 피부질문 Qs
    RadioButton checkBox, checkBox2, checkBox3, checkBox4, checkBox5; // 피부질문 들어갈 5칸
    int index;// list 인덱스 갯수 들어갈 변수
    checkVO vo; // VO 선언
    RadioGroup radioGroup; //RadioGroup 선언
    ViewGroup rootView;
    Button next;

    int[] arr = {2, 3, 4, 5, 6, 13, 14, 18, 22, 25, 26, 27, 28, 30, 31, 32}; // 선택사항 4개인 문제 인덱스 번호

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_baumann, container, false);

        init();

        next = rootView.findViewById(R.id.next);
        next.setOnClickListener(this::onClick);

        // option1 체크박스가 눌렸을 때
        rootView.findViewById(R.id.checkBox).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Checked(v); // 체크되었을 때 동작코드
            }
        });
        // option2 체크박스가 눌렸을 때
        rootView.findViewById(R.id.checkBox2).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Checked(v); // 체크되었을 때 동작코드
            }
        });
        // option3 체크박스가 눌렸을 때
        rootView.findViewById(R.id.checkBox3).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Checked(v); // 체크되었을 때 동작코드
            }
        });
        // option4 체크박스가 눌렸을 때
        rootView.findViewById(R.id.checkBox4).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Checked(v); // 체크되었을 때 동작코드
            }
        });
        // option5 체크박스가 눌렸을 때
        rootView.findViewById(R.id.checkBox5).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Checked(v); // 체크되었을 때 동작코드
            }
        });
        return rootView;
    }

    private void init() {

        type = rootView.findViewById(R.id.type);
        Qs = rootView.findViewById(R.id.Qs);
        checkBox = rootView.findViewById(R.id.checkBox);
        checkBox2 = rootView.findViewById(R.id.checkBox2);
        checkBox3 = rootView.findViewById(R.id.checkBox3);
        checkBox4 = rootView.findViewById(R.id.checkBox4);
        checkBox5 = rootView.findViewById(R.id.checkBox5);

        //지성,건성 문제
        list.add(new checkVO("지성vs건성", "1. 파우더를 사용하지 않고 기초 화장(베이스,파운데이션)만 한 상태에서 2~3시간 후에 당신의 화장 상태는 어떻습니까?",
                "각질이 일어나거나 주름사이에 끼어있다", "매끈하다", "윤기가 흐른다", "줄이 생기고 윤기가 흐른다", "나는 기초화장(파운데이션)을 하지않는다"));
//        list.add(new checkVO("지성vs건성", "2. 비누방울, 거품이 심하게 발생하는 비누를 사용하는 당신의 얼굴 피부는 어떻습니까?", "건조하거나 갈라지는 느낌이 든다",
//                "약간 건조한 느낌이 들지만 갈라지는 느낌은 없다", "정상적인 느낌이다", "기름기가 있는 느낌이다", "나는 비누나 거품이 생기는 클렌저를 사용하지 않는다"));
//        list.add(new checkVO("지성vs건성", "3. 보습제를 사용하지 않으면 얼굴 피부는 탱탱한 느낌입니까?", "항상 그렇다", "때때로 그렇다", "거의 그렇지 않다", "전혀 그렇지 않다"));
//        list.add(new checkVO("지성vs건성", "4. 얼굴에 모공이 있습니까?", "전혀 없다", "때때로 있다", "자주 있다", "항상있다"));
//        list.add(new checkVO("지성vs건성", "5. 얼굴의 T존(이마와 코)에 기름기가 있습니까?", "전혀없다", "때떄로 있다", "자주 있다", "항상 있다"));
//        list.add(new checkVO("지성vs건성", "6. 보습제를 바르고 2~3시간 이후에 양 볼은?", "매우 거칠거나,피부가 벗겨지거나 창백하다", "매끄럽다", "약간 윤기가 흐른다", "윤기가 흐르고 번들거린다 혹은, 나는 보습제를 사용하지 않는다"));
//
//        //민감성,저항성 문제
//        list.add(new checkVO("민감성vs저항성", "7. 얼굴에 붉게 돌출된 병변이 발생한 경험이 있습니까?", "전혀 없다", "거의 없다", "최소한 1개월에 1회 있다", "최소한 1주일에 1회 있다"));
//        list.add(new checkVO("민감성vs저항성", "8. 피부 관리용 제품(클렌저,보습제,색조화장품, 및 화장품 등을 포함)을 사용하면 얼굴에 뾰루지가 나거나 발진, 가려움증, 혹은 따끔거리는 증상 등이 나타난 경험이 있습니까?",
//                "전혀 없다", "거의 없다", "자주 있다", "항상 있다", "난느 얼굴에 피부 관리용 제품을 바르지 않는다"));
//        list.add(new checkVO("민감성vs저항성", "9. 과거에 여드름이나 주사에 대한 진단을 받은 적이 있습니까?", "없다", "친구와 지인이 나에게 이런 증상이 있다고 얘기한다", "진단을 받았다", "심각한 경우에 해당한다",
//                "잘 모르겠다"));
//        list.add(new checkVO("민감성vs저항성", "10. 14k금이 아닌 장신구를 착용할 경우에 피부 발진이 자주 발생합니까?", "전혀 안 나타난다", "거의 안 나타난다", "자주 나타난다", "항상 나타난다", "잘 모르겠다"));
//        list.add(new checkVO("민감성vs저항성", "11. 과거에 아토피 피부염, 습진, 또는 접촉성 피부염(알레르기성 피부 발진)으로 진단을 받은 적이 있습니까?",
//                "없다", "친구들이 나에게 이런 질환이 있는 것 같다고 한다", "있다", "있다, 심각한 경우에 해당한다", "잘 모르겠다"));
//        list.add(new checkVO("민감성vs저항성", "12. 향로가 포함된 거품 입욕제, 마사지 오일, 혹은 바디 로션 등을 사용하면 피부 발진이 생기거나, 가렵거나 건조한 느낌이 있습니까?",
//                "전혀 없다", "거의 없다", "자주 나타난다", "항상 나탄다", "나는 이런 종류의 제품을 사용하지 않는다"));
//        list.add(new checkVO("민감성vs저항성", "13. 가족 중에서 아토피 피부염, 습진, 천식, 알레르기로 진단받은 분이 있습니까?", "없다", "가족 중 한 명이 진단 받았다",
//                "가족 중 몇 명이 진단 받았다", "가족 중 많은 사람이 피부염, 습진, 천식, 알레르기를 가지고 있다", "잘 모르겠다"));
//        list.add(new checkVO("민감성vs저항성", "14. 운동, 스트레스 또는 격한 감정(분노 등), 음주, 맵거나 뜨거운 음식 섭취에 의해 얼굴과 목이 붉어집니까?", "전혀 그런 적이 없다",
//                "때때로 그렇다", "자주 그렇다", "항상 그렇다"));
//        list.add(new checkVO("민감성vs저항성", "15. 얼굴이나 코에 붉거나 푸른색의 혈관이 얼마나 많이 보입니까(혹은 치룢 전에 얼마나 많이 보였습니까)?", "없다",
//                "드물게 보인다(코를 포함한 얼굴 전체에서 1~3개)", "약간 보인다(코를 포함한 얼굴 전체에서 4~6개)", "많이 보인다(코를 포함한 얼굴 전체에서 7개 이상)"));
//
//        //색소성,비색소성 문제
//        list.add(new checkVO("색소성vs비색소성", "16. 여드름 혹은 피부 속으로 파고 들어간 모발에 의해 짙은 갈색이나 짙은 점이 생깁니까?", "전혀 생기지 않는다", "때때로 생긴다", "자주 생긴다",
//                "항상 생긴다", "여드름이나 피부 속으로 파고 들어간 모발이 전혀 없다"));
//        list.add(new checkVO("색소성vs비색소성", "17. 얼굴의 짙은 점은 햇빛에 의해 악화됩니까?", "짙은 점이 전혀 없다", "잘 모르겠다", "약간 나빠진다", "많이 나빠진다", "" +
//                "매일 자외선차단제를 얼굴에 바르고 햇빛을 피한다"));
//        list.add(new checkVO("색소성vs비색소성", "18. 과거에 얼굴 기미(옅거나 짙은 갈색 혹은 회색 반점)로 진단 받은 적이 있습니까?", "없다", "한 번 있었지만 소실되었다", "있다", "있으며 심각한 경우이다",
//                "잘 모르겠다"));
//        list.add(new checkVO("색소성vs비색소성", "19. 얼굴, 가슴, 등 혹은 양 팔에 작은 갈색 점(주근깨, 일광 흑자)이있거나 있었던 적이 있습니까?", "없다", "약간 있다(1~5개)",
//                "많이 있다(6~15개", "대단히 많다(16개 이상)"));
//        list.add(new checkVO("색소성vs비색소성", "20. 며칠동안 계속해서 햇빛에 노출되면 어떤 일이 일어납니까?", "햇빛에 타서 물집이 생겼으나 피부색은 변하지 않는다",
//                "피부색이 약간 짙어졌다", "피부색이 많이 짙어졌다", "원래 피부색이 짙어서 더 짙어졌는지 구분이 어렵다", "잘 모르겠다"));
//        list.add(new checkVO("색소성vs비색소성", "21. 햇볕에 나가면 주근깨(작은 1~2 mm 크기의 편평한 점)가 생기나요?", "전혀 생기지 않는다", "매년 약간씩 작은 주근깨가 생긴다",
//                "자주 주근깨가 생긴다", "피부가 이미 짙어서 주근깨가 있는 지 구분이 어렵다", "햇볕에 전혀 나가지 않는다"));
//        list.add(new checkVO("색소성vs비색소성", "22. 부모님 중의 어느 한 분이라도 주근깨가 있나요?(만약 그렇다면,얼마나 많았는지 지적해주세요 두 분 중 아무도 그렇지 않거나 한 분만 해당하면 질문에 답해 주세요 두분이 모두 있으면 주근깨가 가장 많았던 분과 관련된 질문에 답해 주세요",
//                "없다", "얼굴에 약간 있었다", "얼굴에 많이 있었다", "얼굴, 가슴, 목, 양쪽 어깨에 많이 있었다", "잘 모르겠다"));
//
//        //주름,탱탱함 문제
//        list.add(new checkVO("주름vs탱탱함", "23. 얼굴에 주름이 있나요?", "없다 웃거나 찡그리거나 눈썹을 치켜 올릴 경우에도 없다", "웃거나 찡그리거나 눈썹을 치켜 올릴 때에 있다", "있다 얼굴을 움직이거나 움직임이 없는 안정된 상태에서도 약간 있다",
//                "웃거나 찡그리거나 혹은 눈썹을 치켜 올리지 않아도 주름이 있다"));
//        list.add(new checkVO("주름vs탱탱함", "24. 어머니의 얼굴 피부는 어떻습니까?", "나이보다 5~10년 젋어 보인다/보였다", "나이대로 보인다/보였다", "나이보다 5년 이상 더 젊어 보인다/보였다", "나이보다 5년 이상 더 늙어 보인다/보였다",
//                "모른다 입양되었거나 기억이 안난다"));
//        list.add(new checkVO("주름vs탱탱함", "25. 아버지의 얼굴 피부는 어떻습니까?", "나이보다 5~10년 젊어 보인다/보였다", "나이대로 보인다/보였다", "나이보다 5년 이상 더 젋어 보인다/보였다", "나이보다 5년 이상 더 늙어 보인다/보였다",
//                "모른다 입양되었거나 기억이 안난다"));
//        list.add(new checkVO("주름vs탱탱함", "26. 1년에 2주 이상 계속 피부를 태운 적이 있었습니까?(만약 그랬다면, 몇 년 동안이나 그렇게 했나요? 테니스,낚시,골프,스키 혹은 다른 야외 활동에 의해 피부가 탔을 경우에도 빈도를 세어 주세요",
//                "없다", "1~5년", "5~10년", "10년 초과"));
//        list.add(new checkVO("주름vs탱탱함", "27. 일상생활을 기준으로, 일상생활 중에서 햇빛에 노출 되는 시간은 하루에 얼마나 됩니까?", "별로 없다(흐리거나 구름이 많은 장소에서 주로 살아왔다)",
//                "약간 있다(때떄로 덜 화창한 기후에서 살았지만 규칙적으로 일광이 존재하는 장소에서 살아왔다", "보통이다(꽤 많은 양의 일광이 존재하는 장소에서 살아왔다", "" +
//                "많다(열대,남부,혹은 매우 화창한 장소에서 살아왔다"));
//        list.add(new checkVO("주름vs탱탱함", "28. 나는 얼마나 나이 들어 보인다고 생각합니까?", "내 나이보다 1~5년 더 젊어 보인다", "내 나이대로 보인다", "내 나이보다 5년 더 늙어 보인다", "내 나이보다 5년 이상 더 늙어보인다"));
//        list.add(new checkVO("주름vs탱탱함", "29. 지난 5년 동안에 야외 스포츠나 다른 활동에 의해 피부를 얼마나 자주 태웠습니까?", "전혀 안 태웠다", "1개월에 1회", "1주일에 1회", "매일"));
//        list.add(new checkVO("주름vs탱탱함", "30. 담배를 얼마나 많이 피웁니까?(혹은 담배연기에 얼마나 많이 노출되었나요?)", "전혀 아니다", "두 세 갑", "몇 갑~ 여러 갑", "매일 피운다",
//                "담배를 피운 적은 전혀 없지만 규칙적으로 담배를 피우는 사람과 함께 지냈거나 그들에 의해 양육되었거나 함께 근무하였다"));
//        list.add(new checkVO("주름vs탱탱함", "31. 레니노이드가 포함된 안면 크림(레티놀,레노바", "레틴-A,타조락,디페린,어베이지 등)을 사용했다면 얼마나 오래 혹은 자주 사용하셨습니까?",
//                "여러 해", "때때로", "젊었을 때 여드름 때문에 1회", "전혀 없다"));
//        list.add(new checkVO("주름vs탱탱함", "32. 과일과 채소를 얼마나 자주 섭취하고 있습니까?", "매 끼니마다", "하루 1회", "때때로", "전혀 안 먹는다"));
//        list.add(new checkVO("주름vs탱탱함", "33. 원래의 피부 색깔은(피부를 태우거나 태닝을 하지 않았을 때)?", "짙은 색깔", "중간 색깔", "옅은 색깔", "매우 옅은 색깔"));

        vo = list.get(index);
//        type.setText(vo.getType());
//        Qs.setText(vo.getQs());
//        checkBox.setText(vo.getCheckBox());
//        checkBox2.setText(vo.getCheckBox2());
//        checkBox3.setText(vo.getCheckBox3());
//        checkBox4.setText(vo.getCheckBox4());
//        checkBox5.setText(vo.getCheckBox5());
    }

    public void onClick(View v) {
        radioGroup = rootView.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        if (index < list.size() - 1) {
            temp = true;
            index += 1;

            vo = list.get(index);

            for (int i = 0; i < arr.length; i++) {
                if (temp) {
                    if (index == arr[i]) {
                        checkBox5.setVisibility(View.INVISIBLE);
                        temp = false;
                    } else {
                        checkBox5.setVisibility(View.VISIBLE);
                    }
                }
            }

            match(v);
            baumann(v);
            Toast.makeText(getActivity(), "oil" + scoreoil, Toast.LENGTH_SHORT).show();
            type.setText(vo.getType());
            Qs.setText(vo.getQs());
            checkBox.setText(vo.getCheckBox());
            checkBox2.setText(vo.getCheckBox2());
            checkBox3.setText(vo.getCheckBox3());
            checkBox4.setText(vo.getCheckBox4());
            checkBox5.setText(vo.getCheckBox5());

            //scoreList.add(score);
        } else {
            calculate(v);

            //cameraFragment
            Bitmap bitmap = getArguments().getParcelable("a");

            Bundle bundle = new Bundle(); // 번들을 통해 값 전달
            bundle.putDouble("scoreoil", scoreoil);
            bundle.putDouble("scoresen", scoresen);
            bundle.putDouble("scoremel", scoremel);
            bundle.putDouble("scoretin", scoretin);//번들에 넘길 값 저장
            bundle.putString("result", mbtiDO + mbtiSR + mbtiPN + mbtiWT);
            bundle.putParcelable("a", bitmap);

            // 비트맵 이미지 받아오는지 확인
            if (bitmap != null){
                Log.d("transmit_ok", "yes");
            }else{
                Log.d("transmit_no", "no");
            }

            // 번들에서 프래그먼트3으로 보내는 부분
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment_tab3 fragment_tab3 = new Fragment_tab3();//프래그먼트2 선언
            fragment_tab3.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비

            // 프래그먼트 3번으로 가는부분
            transaction.replace(R.id.container, fragment_tab3);
            transaction.commit();

//            Toast.makeText(getActivity(), "문항이 없습니다.", Toast.LENGTH_LONG).show();
//            Toast.makeText(getActivity(), "oil" + scoreoil + " sen" + scoresen + " mel" + scoremel + " tin" + scoretin, Toast.LENGTH_LONG).show();
//            Toast.makeText(getActivity(), "result: " + mbtiDO + mbtiSR + mbtiPN + mbtiWT, Toast.LENGTH_LONG).show();
        }
    }

    public void baumann(View v) {
        if (scoreoil >= 6) {
            mbtiDO = "D";
        } else if (scoreoil >= 16) {
            mbtiDO = "O";
        }
        if (scoresen >= 9) {
            mbtiSR = "R";
        } else if (scoresen >= 15.5) {
            mbtiSR = "S";
        }
        if (scoremel >= 7) {
            mbtiPN = "N";
        } else if (scoremel >= 15) {
            mbtiPN = "P";
        }
        if (scoretin >= 11) {
            mbtiWT = "T";
        } else if (scoretin >= 22.5) {
            mbtiWT = "W";
        }
    }

    public void Checked(View v) { // 체크되었을 때 동작할 메소드 구현

        isChecked = ((RadioButton) v).isChecked();

        if (isChecked) {
            switch (v.getId()) {
                case R.id.checkBox:
                    score = 1;
                    break;
                case R.id.checkBox2:
                    score = 2;
                    break;
                case R.id.checkBox3:
                    score = 3;
                    break;
                case R.id.checkBox4:
                    score = 4;
                    break;
                case R.id.checkBox5:
                    score = 2.5;
                    break;
            }
        }
    }

    public void match(View v) {
        if (type.getText().equals("지성vs건성")) {
            scoreoil += score;
        } else if (type.getText().equals("민감성vs저항성")) {
            scoresen += score;
        } else if (type.getText().equals("색소성vs비색소성")) {
            scoremel += score;
        } else if (type.getText().equals("주름vs탱탱함")) {
            scoretin += score;
        }
    }

    public void calculate(View v) {

        scoreoil = (scoreoil / 24) * 100;
        scoresen = (scoresen / 36) * 100;
        scoremel = (scoremel / 28) * 100;
        scoretin = (scoretin / 44) * 100;
        if (scoreoil <= 50) {
            scoreoil = scoreoil * 2;
        } else {
            scoreoil = (100 - scoreoil) * 2 + 2;
        }
        if (scoresen <= 50) {
            scoresen = scoresen * 2;
        } else {
            scoresen = (100 - scoresen) * 2 + 2;
        }
        if (scoremel <= 50) {
            scoremel = scoremel * 2;
        } else {
            scoremel = (100 - scoremel) * 2 + 2;
        }
        if (scoretin <= 50) {
            scoretin = scoretin * 2;
        } else {
            scoretin = (100 - scoretin) * 2 + 2;
        }
        scoreavg = (scoreoil + scoresen + scoremel + scoretin) / 4;
    }


}