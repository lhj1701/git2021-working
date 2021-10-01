import { useRef, useState } from "react";
import produce from "immer";

// state 1건에 대한 타입
interface ContactItemState {
  id: number;
  name : string | undefined;
  phone : string | undefined;
  email : string | undefined;
  createdTime: number;
  modifyTime?: number;
  isEdit?: boolean; // 수정모드인지 여부
}

const getTimeString = (unixtime: number) => {
  const dateTime = new Date(unixtime);
  return `${dateTime.toLocaleDateString()} ${dateTime.toLocaleTimeString()}`;
};

const ContactInlineEdit = () => {
  const [contactList, setContactList] = useState<ContactItemState[]>([]);
  // 데이터 로딩처리 여부를 표시
  const [isLoading, setLoading] = useState<boolean>(true);

  const nameRef = useRef<HTMLInputElement>(null);
  const phoneRef = useRef<HTMLInputElement>(null);
  const mailRef = useRef<HTMLInputElement>(null);
  const formRef = useRef<HTMLFormElement>(null);
  const trRef = useRef<HTMLTableRowElement>(null);

  const add = (e: React.KeyboardEvent<HTMLInputElement> | null) => {
    // 이벤트 객체가 있을 때는 입력박스에서 엔터 입력
    if (e) {
      if (e.code !== "Enter") return;
    }
  
    const contact: ContactItemState = {
      id: contactList.length > 0 ? contactList[0].id + 1 : 1,
      // optional chaning
      name: nameRef.current?.value,
      phone:phoneRef.current?.value,
      email: mailRef.current?.value,
      createdTime: new Date().getTime(),
    };
  
    // console.log(todoList);
    // immer 없이 새로운 배열을 생성하여 state 변경
    // setTodoList([todo, ...todoList]);
  
    // current state -> draft state -> next state
    // draft state를 조작함
    setContactList(
      // produce(([draftstate변수]) => {draft state 변수 조작})
      // 반환 객체는 변경된 state(next state)
      produce((state) => {
        // draft state 배열에 추가
        // draft state의 타입은 TodoItemState[]
        state.unshift(contact);
      })
    );
  
    // 입력값 초기화
    formRef.current?.reset();
  };

  const del = (id: number, index: number) => {
    // immer로 state 배열 직접 조작(index로 삭제)
    setContactList(
      produce((state) => {
        state.splice(index, 1);
      })
    );
  };

  const edit = (id: number, mod: boolean) => {
    // immer를 사용해서 해당 요소를 변경
    setContactList(
      produce((state) => {
        // 해당 id값에 해당하는 요소를 찾음
        const item = state.find((item) => item.id === id);
        if (item) {
          item.isEdit = mod;
        }
      })
    );
  };

  const save = (id: number, index: number) => {
    const editName = trRef.current?.querySelectorAll("td")[index].querySelector("input");
    const editPhone = trRef.current?.querySelectorAll("td")[index].querySelector("input");
    const editMail = trRef.current?.querySelectorAll("td")[index].querySelector("input");

    // immer를 사용하여 해당 요소를 직접변경
    setContactList(
      produce((state) => {
        const item = state.find((item) => item.id === id);
        if (item) {
          item.name = editName?.value;
          item.phone = editPhone?.value;
          item.email = editMail?.value;
          item.modifyTime = new Date().getTime();
          item.isEdit = false;
        }
      })
    );
  };
  return (
    <div className="mx-auto">
      <h2 className="text-center my-5">Contact</h2>
      <form
        className="d-flex"
        ref={formRef}
        /* 
          event.preventDefault(); 
          - 기본이벤트 작업을 처리하지 않음 
          - submit form
        */
        onSubmit={(e) => e.preventDefault()}
      >
        <input
          type="text"
          className="form-control me-2"
          placeholder="이름"
          ref={nameRef}
          onKeyPress={(e) => {
            add(e);
          }}
        />
        <input
          type="text"
          className="form-control me-2"
          placeholder="전화번호"
          ref={phoneRef}
          onKeyPress={(e) => {
            add(e);
          }}
        />
        <input
          type="text"
          className="form-control me-2"
          placeholder="이메일"
          ref={mailRef}
          onKeyPress={(e) => {
            add(e);
          }}
        />
        <button
          type="button"
          className="btn btn-primary text-nowrap"
          onClick={() => {
            add(null);
          }}
        >추가</button>
      </form>
<table className="table table-striped text-center mx-auto">
  <thead>
    <tr><th>이름</th><th>전화번호</th><th>이메일</th><th>작성일시</th></tr>
  </thead>
  <tbody>
  {contactList.map((item, index) => (
          <tr key={item.id}>
              {/* 보기모드일 때 보이는 내용 */}
              {!item.isEdit && <td>{item.name}</td>}
              {!item.isEdit && <td>{item.phone}</td>}
              {!item.isEdit && <td>{item.email}</td>}
              {!item.isEdit && (<td>
                  {getTimeString(
                    item.modifyTime ? item.modifyTime : item.createdTime
                  )}
                </td>
              )}
            
              {/* 수정모드일 때 보이는 입력폼 */}
              <td>{item.isEdit && (
                <input type="text" defaultValue={item.name}/>)}</td>
              <td>{item.isEdit && (
              <input type="text" defaultValue={item.phone} />)}</td>
              <td>{item.isEdit && (
              <input type="text" defaultValue={item.email} />)}</td>

              
              {/* 수정모드일 때 보이는 버튼 */}
              {item.isEdit && (
              <button
                className="btn btn-outline-secondary btn-sm ms-2 me-1 text-nowrap"
                onClick={() => {
                  save(item.id, index);
                }}
              >
                저장
              </button>
            )}
            {item.isEdit && (
              <button
                className="btn btn-outline-secondary btn-sm text-nowrap"
                onClick={() => {
                  edit(item.id, false);
                }}
              >
                취소
              </button>
            )}
              
            
            {/* 보기모드일 때 보이는 버튼 */}
            {!item.isEdit && (
              <button
                className="btn btn-outline-secondary btn-sm ms-2 me-1 text-nowrap"
                onClick={() => {
                  edit(item.id, true);
                }}
              >
                수정
              </button>
            )}
            {!item.isEdit && (
              <button
                className="btn btn-outline-secondary btn-sm text-nowrap"
                onClick={() => {
                  del(item.id, index);
                }}
              >
                삭제
              </button>
            )}
          </tr>
        ))}
  </tbody>
  </table>
      {/* <tr ref={trRef}> */}
        {/* 로딩중 처리 표시 */}
        {isLoading && (
            <div className="spinner-border text-primary" role="status">
              <span className="visually-hidden">Loading...</span>
            </div>
        )}
        {/* 빈 데이터 표시 */}
        {!isLoading && contactList.length === 0 && (
          <li className="list-group-item">데이터가 없습니다.</li>
        )}
        {/* 데이터와 UI요소 바인딩 */}
        
      {/* </tr> */}
    </div>
  );
};



export default ContactInlineEdit;